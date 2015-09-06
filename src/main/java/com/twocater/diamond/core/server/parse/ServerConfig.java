package com.twocater.diamond.core.server.parse;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ServerConfig {

    private Properties globalParams;
    private List<ConnectorConfig> connectorConfigs = Collections.emptyList();
    private List<ContextServiceConfig> serviceConfigs = Collections.emptyList();
    private List<ContextFilterConfig> filterConfigs = Collections.emptyList();


    public Properties getGlobalParams() {
        return globalParams;
    }

    public void setGlobalParams(Properties globalParams) {
        this.globalParams = globalParams;
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
}
