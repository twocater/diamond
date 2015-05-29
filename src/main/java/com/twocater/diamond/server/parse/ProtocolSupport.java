/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server.parse;

/**
 *
 * @author cpaladin
 */
public enum ProtocolSupport {

    http("com.twocater.diamond.netty.http.HttpConnectorFactory", "com.twocater.diamond.protocol.http.HttpContext");

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
