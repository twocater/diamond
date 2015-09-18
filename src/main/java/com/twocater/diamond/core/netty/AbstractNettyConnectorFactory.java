/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.Connector;
import com.twocater.diamond.core.server.Server;
import com.twocater.diamond.core.server.parse.ConnectorConfig;

/**
 * @author cpaladin
 */
public abstract class AbstractNettyConnectorFactory implements ConnectorFactory {

    private ConnectorConfig connectorConfig;

    public void setConnectorConfig(ConnectorConfig connectorConfig) {
        this.connectorConfig = connectorConfig;
    }

    protected abstract NettyHandlerFactory createHandlerFactory();

    @Override
    public Connector createConnector(Server server) throws Exception {
        NettyHandlerFactory nettyHandlerFactory;
        if (connectorConfig.getHandlerFactory() != null) {
            nettyHandlerFactory = (NettyHandlerFactory) Class.forName(connectorConfig.getHandlerFactory()).newInstance();
        } else {
            nettyHandlerFactory = createHandlerFactory();
        }

        return new NettyConnector(connectorConfig, nettyHandlerFactory, server);
    }

}
