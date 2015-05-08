package com.twocater.diamond.api.service;

import java.util.Set;

public interface ServiceConfig {

    int getOrder();

    String getServiceName();

//	Context getContext();
    String getInitParameter(String name);

    Set<String> getInitParameterNames();
}
