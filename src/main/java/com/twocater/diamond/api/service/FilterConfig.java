package com.twocater.diamond.api.service;

import java.util.Set;

public interface FilterConfig {

    String getFilterName();

//	Context getContext();
    String getInitParameter(String name);

    Set<String> getInitParameterNames();

}
