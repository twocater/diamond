package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.ServiceConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ServiceConfigImpl implements ServiceConfig {

    private String serviceName;
    private Map<String, String> params;
    private int order;

    ServiceConfigImpl(String serviceName, int order, Map<String, String> params) {
        this.serviceName = serviceName;
        this.order = order;
        this.params = params;
    }

    @Override
    public String getInitParameter(String name) {
        return params.get(name);
    }

    @Override
    public Set<String> getInitParameterNames() {
        return params.keySet();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public int getOrder() {
        return order;
    }

}
