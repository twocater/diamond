package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.*;
import com.twocater.diamond.core.bootstrap.LifeCycle;
import com.twocater.diamond.core.server.parse.ContextFilterConfig;
import com.twocater.diamond.core.server.parse.ContextServiceConfig;
import com.twocater.diamond.core.server.parse.ServerConfig;
import com.twocater.diamond.kit.mapping.CacheSingleMapping;
import com.twocater.diamond.kit.mapping.MatchMapping;
import com.twocater.diamond.kit.mapping.SingleMapping;
import com.twocater.diamond.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author cpaladin
 */
public abstract class AbstractContext implements ServerContext, LifeCycle {
    private static final Logger log = LoggerFactory.getLogger("com.twocater.diamond.server");

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
     * 监听器
     */
    protected List<EventListener> listeners = new ArrayList<EventListener>();

    protected SingleMapping serviceMapping;

    private ServerConfig serverConfig;

    @Override
    public void init() throws Exception {
        serverConfig = server.getServerConfig();

        initFilter();
        initService();
    }

    @Override
    public void destroy() throws Exception {

    }

    /**
     * init services
     *
     * @throws Exception
     */

    private void initService() throws Exception {
        // service
        Map<String, String> serviceMappings = new HashMap<String, String>();// 路径-->名称
        serviceMapping = new CacheSingleMapping(createServiceMapping(serviceMappings)); // 映射服务
        List<ContextServiceConfig> serviceConfigs = serverConfig.getServiceConfigs();
        Collections.sort(serviceConfigs);
        for (ContextServiceConfig serviceConfig : serviceConfigs) {
            String name = serviceConfig.getServiceName();
            if (services.containsKey(name)) {
                log.warn(LogMessage.EXIST_SERVICE + name);
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
            log.info(LogMessage.INIT_SERVICE + name);
        }
    }

    protected void initFilter() throws Exception {
        MatchMapping filterMapping = createFilterMapping();
        List<ContextFilterConfig> filterConfigs = serverConfig.getFilterConfigs();
        Set<String> nameSet = new HashSet<String>();
        // init filter
        for (ContextFilterConfig filterConfig : filterConfigs) {
            String filterName = filterConfig.getFilterName();
            if (nameSet.contains(filterName)) {
                log.warn(LogMessage.EXIST_FILTER + filterName);
                continue;
            }
            nameSet.add(filterName);
            Filter filter = (Filter) createObject(filterConfig.getFilterClass());
            FilterContainer filterContainer = new FilterContainer(filter, filterConfig.getFilterPaths(), filterMapping);
            FilterConfig config = new FilterConfigImpl(filterName, this, filterConfig.getParams());
            filterContainer.init(config);
            filters.add(filterContainer);
            log.info(LogMessage.INIT_FILTER + filterName);
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

    protected abstract ContextRequest createRequest(ServerRequest serverRequest);

    protected abstract MatchMapping createFilterMapping();

    protected abstract SingleMapping createServiceMapping(Map<String, String> mappings);

    class LogMessage {
        public static final String EXIST_SERVICE = "service exists:";
        public static final String EXIST_FILTER = "filter exists:";
        public static final String EXIST_DEPENDED_SERVICE = "dependedService exists:";
        public static final String EXIST_ONE_OPERATION = "oneOperation exists:";
        public static final String EXIST_TASK = "task exists:";

        public static final String INIT_LISTENER = "init [listener]:";
        public static final String INIT_SERVICE = "init [service]:";
        public static final String INIT_FILTER = "init [filter]:";
        public static final String INIT_DEPENDED_SERVICE = "init [dependedService]:";
        public static final String INIT_ONE_OPERATION = "init [oneOperation]:";
        public static final String INIT_TASK = "init [task]:";

    }

}
