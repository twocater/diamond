package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.*;
import com.twocater.diamond.core.bootstrap.LifeCycle;
import com.twocater.diamond.core.server.parse.ContextFilterConfig;
import com.twocater.diamond.core.server.parse.ContextListenerConfig;
import com.twocater.diamond.core.server.parse.ContextServiceConfig;
import com.twocater.diamond.core.server.parse.ServerConfig;
import com.twocater.diamond.kit.mapping.CacheSingleMapping;
import com.twocater.diamond.kit.mapping.MatchMapping;
import com.twocater.diamond.kit.mapping.SingleMapping;
import com.twocater.diamond.util.ExceptionUtil;
import com.twocater.diamond.util.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author cpaladin
 */
public abstract class AbstractContext implements ServerContext, LifeCycle {
    private static final Logger log = LoggerFactory.getLogger(LoggerConstant.SERVER);

    protected Server server;
    /**
     * 过滤器，初始化时设定，有顺序
     */
    protected List<FilterContainer> filters = new ArrayList<FilterContainer>();
    /**
     * 服务，初始化时设定，无顺序
     */
    protected Map<String, ServiceContainer> services = new HashMap<String, ServiceContainer>();

    /**
     * 监听器,初始化时设定，按增加先后顺序
     */
    protected List<EventListener> listeners = new ArrayList<EventListener>();

    protected SingleMapping serviceMapping;

    private ServerConfig serverConfig;
    protected volatile boolean initialized = false;

    @Override
    public void init() throws Exception {
        if (initialized) {
            return;
        }
        long start = System.currentTimeMillis();
        getLog().info("init context:" + this.getClass().getName());

        serverConfig = server.getServerConfig();

        // init listeners
        initListener();
        // context is initialized, notify  the ContextListener by calling the contextInitialized method of ContextListener
        for (EventListener listener : this.listeners) {
            if (listener instanceof ContextListener) {
                ((ContextListener) listener).contextInitialized(this);
            }
        }
        initFilter();
        initService();

        long end = System.currentTimeMillis();
        getLog().info("init context:[{}]" + " success. [{} MS]", new Object[]{this.getClass().getName(), (end - start)});
        initialized = true;
    }

    @Override
    public void destroy() throws Exception {
        if (!initialized) {
            return;
        }
        long start = System.currentTimeMillis();
        getLog().info("destroy context:" + this.getClass().getName());
        // context is destroyed, notify the ContextListener by calling the contextDestroyed method of ContextListener
        for (EventListener eventListener : listeners) {
            if (eventListener instanceof ContextListener) {
                try {
                    ((ContextListener) eventListener).contextDestroyed(this);
                } catch (Exception e) {
                    log.error("ContextListener.contextDestroyed:" + eventListener.getClass().getName(), ExceptionUtil.getExceptionInfo(e));
                }
            }
        }
        listeners.clear();

        long end = System.currentTimeMillis();
        getLog().info("destroy context:[{}]" + " success. [{} MS]", new Object[]{this.getClass().getName(), (end - start)});
        initialized = false;
    }

    /**
     * init services
     *
     * @throws Exception
     */

    private void initService() throws Exception {
        List<ContextServiceConfig> serviceConfigFromAnnotation = AnnotationSupport.getServiceConfigFromAnnotation();
        serverConfig.getServiceConfigs().addAll(serviceConfigFromAnnotation);

        for (ContextServiceConfig c : serviceConfigFromAnnotation) {
            log.info("@annotation service:{},{},{}", new Object[]{c.getServiceName(), c.getServiceClass(), c.getOrder()});
        }

        // service
        Map<String, String> serviceMappings = new HashMap<String, String>();// 路径-->名称
        serviceMapping = new CacheSingleMapping(createServiceMapping(serviceMappings)); // 映射服务
        List<ContextServiceConfig> serviceConfigs = serverConfig.getServiceConfigs();
        Collections.sort(serviceConfigs);
        for (ContextServiceConfig serviceConfig : serviceConfigs) {
            String name = serviceConfig.getServiceName();
            if (services.containsKey(name)) {
                log.warn(LogMessage.EXIST_SERVICE + "[{},{},{}]", new Object[]{name, serviceConfig.getServiceClass(), serviceConfig.getOrder()});
                continue;
            }
            Service service = (Service) createObject(serviceConfig.getServiceClass());
            ServiceConfig config = new ServiceConfigImpl(name, serviceConfig.getOrder(), serviceConfig.getParams());
            ServiceContainer serviceContainer = new ServiceContainer(service);
            serviceContainer.init(config);
            services.put(name, serviceContainer);
            for (String url : serviceConfig.getServicePaths()) {
                serviceMappings.put(url, name);
            }
            log.info(LogMessage.INIT_SERVICE + "[{},{},{}]", new Object[]{name, serviceConfig.getServiceClass(), serviceConfig.getOrder()});
        }
    }

    /**
     * init filters
     *
     * @throws Exception
     */
    protected void initFilter() throws Exception {
        List<ContextFilterConfig> filterConfigFromAnnotation = AnnotationSupport.getFilterConfigFromAnnotation();
        serverConfig.getFilterConfigs().addAll(filterConfigFromAnnotation);
        for (ContextFilterConfig c : filterConfigFromAnnotation) {
            log.info("@annotation filter:{},{},{}", new Object[]{c.getFilterName(), c.getFilterClass(), c.getOrder()});
        }


        MatchMapping filterMapping = createFilterMapping();
        List<ContextFilterConfig> filterConfigs = serverConfig.getFilterConfigs();
        Collections.sort(filterConfigs);
        Set<String> nameSet = new HashSet<String>();
        // init filter
        for (ContextFilterConfig filterConfig : filterConfigs) {
            String filterName = filterConfig.getFilterName();
            if (nameSet.contains(filterName)) {
                log.warn(LogMessage.EXIST_FILTER + "[{},{},{}]", new Object[]{filterName, filterConfig.getFilterClass(), filterConfig.getOrder()});
                continue;
            }
            nameSet.add(filterName);
            Filter filter = (Filter) createObject(filterConfig.getFilterClass());
            FilterContainer filterContainer = new FilterContainer(filter, filterConfig.getFilterPaths(), filterMapping);
            FilterConfig config = new FilterConfigImpl(filterName, this, filterConfig.getParams());
            filterContainer.init(config);
            filters.add(filterContainer);
            log.info(LogMessage.INIT_FILTER + "[{},{},{}]", new Object[]{filterName, filterConfig.getFilterClass(), filterConfig.getOrder()});
        }
    }

    /**
     * init listeners
     *
     * @throws Exception
     */
    protected void initListener() throws Exception {
        List<ContextListenerConfig> listenerConfigFromAnnotation = AnnotationSupport.getListenerConfigFromAnnotation();
        serverConfig.getListenerConfigs().addAll(listenerConfigFromAnnotation);
        for (ContextListenerConfig c : listenerConfigFromAnnotation) {
            log.info("@annotation listener:{},{}", new Object[]{c.getListenerClass(), c.getOrder()});
        }

        List<ContextListenerConfig> listenerConfigs = serverConfig.getListenerConfigs();
        Collections.sort(listenerConfigs);
        Set<String> nameSet = new HashSet<String>();
        for (ContextListenerConfig listenerConfig : listenerConfigs) {
            if (nameSet.contains(listenerConfig.getListenerClass())) {
                log.warn(LogMessage.EXIST_LISTENER + "[{},{}]", new Object[]{listenerConfig.getListenerClass(), listenerConfig.getOrder()});
                continue;
            }
            nameSet.add(listenerConfig.getListenerClass());
            EventListener eventListener = (EventListener) createObject(listenerConfig.getListenerClass());
            this.listeners.add(eventListener);
            log.info(LogMessage.INIT_LISTENER + "[{},{}]", new Object[]{listenerConfig.getListenerClass(), listenerConfig.getOrder()});
        }
    }

    protected Object createObject(String clazz) throws Exception {
        return Class.forName(clazz).newInstance();
    }

    public List<EventListener> getListeners() {
        return listeners;
    }

    public SingleMapping getServiceMapping() {
        return serviceMapping;
    }

    @Override
    public void handle(ServerRequest serverRequest) throws Exception {
        boolean success = false;
        ContextRequest request = createRequest(serverRequest);
        try {
            // request initialize
            for (EventListener eventListener : listeners) {
                if (eventListener instanceof RequestListener) {
                    try {
                        ((RequestListener) eventListener).requestInitialized(request);
                    } catch (Exception e) {
                        log.error(this.getClass().getName() + ".requestInitialized:" + eventListener.getClass().getName(), ExceptionUtil.getExceptionInfo(e));
                        throw e;
                    }
                }
            }

            // handle service
            FilterChain chain = new ContextFilterChain(services, filters);
            chain.doFilter(request);
            success = true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (success) {
                // request destroy
                for (EventListener eventListener : listeners) {
                    if (eventListener instanceof RequestListener) {
                        try {
                            ((RequestListener) eventListener).requestDestroyed(request);
                        } catch (Exception e) {
                            log.error(this.getClass().getName() + ".requestDestroyed:" + eventListener.getClass().getName(), ExceptionUtil.getExceptionInfo(e));
                            throw e;
                        }
                    }
                }
                request.response();
            }
        }
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Set<String> getInitParameterNames() {
        return this.serverConfig.getParams().keySet();
    }

    @Override
    public String getInitParameter(String name) {
        return this.serverConfig.getParams().get(name);
    }

    protected abstract ContextRequest createRequest(ServerRequest serverRequest);

    protected abstract MatchMapping createFilterMapping();

    protected abstract SingleMapping createServiceMapping(Map<String, String> mappings);

    class LogMessage {
        public static final String EXIST_SERVICE = "service exists:";
        public static final String EXIST_FILTER = "filter exists:";
        public static final String EXIST_LISTENER = "listener exists:";

        public static final String INIT_LISTENER = "init listener:";
        public static final String INIT_SERVICE = "init service:";
        public static final String INIT_FILTER = "init filter:";

    }

}
