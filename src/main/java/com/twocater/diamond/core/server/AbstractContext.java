package com.twocater.diamond.core.server;

import com.twocater.diamond.core.bootstrap.LifeCycle;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.Service;
import com.twocater.diamond.api.service.ServiceConfig;
import com.twocater.diamond.core.server.parse.ContextServiceConfig;
import com.twocater.diamond.core.server.parse.ServerConfig;
import com.twocater.kit.mapping.CacheSingleMapping;
import com.twocater.kit.mapping.MatchMapping;
import com.twocater.kit.mapping.SingleMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cpaladin
 */
public abstract class AbstractContext implements ServerContext, LifeCycle {

    protected Server server;
    /**
     * 过滤器，初始化时设定，有顺序
     */
    protected List<FilterContainer> filters = new ArrayList<FilterContainer>();
    /**
     * 服务，初始化时设定，无顺序
     */
    protected Map<String, ServiceContainer> services = new HashMap<String, ServiceContainer>();

    protected SingleMapping serviceMapping;

    private ServerConfig serverConfig;

    @Override
    public void init() throws Exception {
        serverConfig = server.getServerConfig();

        initService();
    }

    @Override
    public void destroy() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
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
                // getLog().log(LogMessage.EXIST_SERVICE + name);
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
            // getLog().log(LogMessage.INIT_SERVICE + name);
        }
    }

    protected Object createObject(String clazz) throws Exception {
        return Class.forName(clazz).newInstance();
    }

    @Override
    public void handle(ServerRequest request) throws Exception {
        boolean success = false;
        ContextRequest contextRequest = createRequest(request);
        try {
            FilterChain chain = new ContextFilterChain(services, filters);
            chain.doFilter(contextRequest);
            success = true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (success) {
                contextRequest.response();
            }
        }
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    public SingleMapping getServiceMapping() {
        return serviceMapping;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    protected abstract ContextRequest createRequest(ServerRequest serverRequest);

    protected abstract MatchMapping createFilterMapping();

    protected abstract SingleMapping createServiceMapping(Map<String, String> mappings);

}
