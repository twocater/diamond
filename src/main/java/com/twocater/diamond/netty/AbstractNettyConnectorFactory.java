/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.Connector;
import com.twocater.diamond.server.Server;
import com.twocater.diamond.server.parse.ConnectorConfig;

/**
 *
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
        NettyHandlerFactory nettyHandlerFactory = createHandlerFactory();
        if (connectorConfig.getHandlerFactory() != null) {
            nettyHandlerFactory = (NettyHandlerFactory) Class.forName(connectorConfig.getHandlerFactory()).newInstance();
        }

        return new NettyConnector(connectorConfig, nettyHandlerFactory, server);
    }

}
