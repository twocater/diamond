package com.twocater.diamond.spring;

import java.util.List;
import java.util.Properties;

public class ServerConfig {
	private Properties globalParams;
	private List<ConnectorConfig> connectorConfigs;

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

}
