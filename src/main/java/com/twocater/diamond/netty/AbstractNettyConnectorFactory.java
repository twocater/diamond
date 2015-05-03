/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.Connector;
import com.twocater.diamond.spring.ConnectorConfig;

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
    public Connector createConnector() {
        NettyHandlerFactory nettyHandlerFactory = connectorConfig.getNettyHandlerFactory();
        if (nettyHandlerFactory == null) {
            nettyHandlerFactory = createHandlerFactory();
        }
        return new NettyConnector(connectorConfig, nettyHandlerFactory);
    }

}
