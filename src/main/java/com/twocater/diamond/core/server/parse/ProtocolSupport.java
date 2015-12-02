/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.server.parse;

/**
 *
 * @author cpaladin
 */
public enum ProtocolSupport {

    http("com.twocater.diamond.core.netty.http.HttpConnectorFactory", "com.twocater.diamond.core.protocol.http.HttpContext"),
    tcp("com.twocater.diamond.core.netty.tcp.TcpConnectorFactory","com.twocater.diamond.core.protocol.tcp.TcpContext");

    private final String connectorFactory;
    private final String serverContext;

    private ProtocolSupport(String connectorFactory, String serverContext) {
        this.connectorFactory = connectorFactory;
        this.serverContext = serverContext;
    }

    public String getConnectorFactory() {
        return connectorFactory;
    }

    public String getServerContext() {
        return serverContext;
    }

}
