/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.ServerContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author cpaladin
 */
public abstract class AbstractHandlerFactory implements NettyHandlerFactory, CoderFactory {

    protected ServerContext serverContext;
    protected boolean keepAlive;
    protected int bussThread;


    public AbstractHandlerFactory() {
    }

    public void setBussThread(int bussThread) {
        this.bussThread = bussThread;
    }

    public void setServerContext(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }


    @Override
    public ChannelInitializer createChildHandler(int timeout) {
        return new NettyChannelInitializer(this, timeout, bussThread);
    }

}
