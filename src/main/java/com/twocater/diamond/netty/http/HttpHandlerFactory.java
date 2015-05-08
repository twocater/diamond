/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.netty.CoderFactory;
import com.twocater.diamond.netty.NettyChannelInitializer;
import com.twocater.diamond.netty.NettyHandlerFactory;
import com.twocater.diamond.server.Server;
import io.netty.channel.ChannelInitializer;

/**
 *
 * @author cpaladin
 */
public class HttpHandlerFactory implements NettyHandlerFactory {

    private final CoderFactory coderFactory;

    public HttpHandlerFactory(Server server, CoderFactory coderFactory) {
        if (coderFactory == null) {
            coderFactory = new HttpCoderFactory(server);
        }
        this.coderFactory = coderFactory;
    }

    @Override
    public ChannelInitializer createChildHandler() {
        return new NettyChannelInitializer(coderFactory);
    }

}
