/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond;

/**
 *
 * @author cpaladin
 */
public class NettyServer implements Server {

    private NettyServerConfig nettyServerConfig;
    private NettyConnector nettyConnector;

    public NettyServer(NettyServerConfig nettyServerConfig) {
        this.nettyServerConfig = nettyServerConfig;
    }

    @Override
    public void startup() throws Exception {
        nettyConnector = new NettyConnector();
        nettyConnector.bind();

    }

    @Override
    public void shutdown() throws Exception {
        nettyConnector.unbind();
    }

}
