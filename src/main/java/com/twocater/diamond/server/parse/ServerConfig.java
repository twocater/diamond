package com.twocater.diamond.server.parse;

import java.util.List;
import java.util.Properties;

public class ServerConfig {

    private Properties globalParams;
    private List<ConnectorConfig> connectorConfigs;
    private List<ContextServiceConfig> serviceConfigs;

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

}
