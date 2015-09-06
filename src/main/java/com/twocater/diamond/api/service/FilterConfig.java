package com.twocater.diamond.api.service;

import com.twocater.diamond.core.server.ServerContext;

import java.util.Set;

public interface FilterConfig {

    String getFilterName();

    ServerContext getContext();

    String getInitParameter(String name);

    Set<String> getInitParameterNames();

}
