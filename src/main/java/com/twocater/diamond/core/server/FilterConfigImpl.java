package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.FilterConfig;

import java.util.Map;
import java.util.Set;

class FilterConfigImpl implements FilterConfig {
    private String filterName;
    private ServerContext context;
    private Map<String, String> params;

    FilterConfigImpl(String filterName, ServerContext context, Map<String, String> params) {
        this.filterName = filterName;
//		this.context = context;
        this.params = params;
    }

    @Override
    public String getFilterName() {
        return filterName;
    }

    @Override
    public ServerContext getContext() {
        return context;
    }

    @Override
    public String getInitParameter(String name) {
        return params.get(name);
    }

    @Override
    public Set<String> getInitParameterNames() {
        return params.keySet();
    }

}
