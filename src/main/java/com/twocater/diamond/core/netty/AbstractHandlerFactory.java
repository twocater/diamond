/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.ServerContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;

/**
 *
 * @author cpaladin
 */
public abstract class AbstractHandlerFactory implements NettyHandlerFactory, CoderFactory {

    protected ServerContext serverContext;

    public AbstractHandlerFactory() {
    }

    public void setServerContext(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public ChannelInitializer createChildHandler() {
        return new NettyChannelInitializer(this);
    }

    @Override
    public ChannelHandlerAdapter createServerHandler() {
        return null;
    }

}
