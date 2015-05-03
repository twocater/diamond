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

    http("com.twocater.diamond.netty.http.HttpConnectorFactory");
    private final String connectorFactory;

    private ProtocolSupport(String connectorFactory) {
        this.connectorFactory = connectorFactory;
    }

    public String getConnectorFactory() {
        return connectorFactory;
    }

}
