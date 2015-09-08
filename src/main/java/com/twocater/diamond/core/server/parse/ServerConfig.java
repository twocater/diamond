package com.twocater.diamond.core.server.parse;

import java.util.*;

public class ServerConfig {

    private Map<String, String> params;
    private List<ConnectorConfig> connectorConfigs = Collections.emptyList();
    private List<ContextServiceConfig> serviceConfigs = Collections.emptyList();
    private List<ContextFilterConfig> filterConfigs = Collections.emptyList();
    private List<ContextListenerConfig> listenerConfigs = Collections.emptyList();


    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    public List<ConnectorConfig> getConnectorConfigs() {
        return connectorConfigs;
    }

    public void setConnectorConfigs(List<ConnectorConfig> connectorConfigs) {
        this.connectorConfigs = connectorConfigs;
    }

    public List<ContextServiceConfig> getServiceConfigs() {
        return serviceConfigs;
    }

    public void setServiceConfigs(List<ContextServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    public List<ContextFilterConfig> getFilterConfigs() {
        return filterConfigs;
    }

    public void setFilterConfigs(List<ContextFilterConfig> filterConfigs) {
        this.filterConfigs = filterConfigs;
    }

    public List<ContextListenerConfig> getListenerConfigs() {
        return listenerConfigs;
    }

    public void setListenerConfigs(List<ContextListenerConfig> listenerConfigs) {
        this.listenerConfigs = listenerConfigs;
    }
}
