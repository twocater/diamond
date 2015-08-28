/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.core.server.ServerRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author cpaladin
 */
public abstract class Nettyhandler extends ChannelInboundHandlerAdapter implements ConnectChannelFactory {

    // private final Server server;
    private final ServerContext serverContext;

    protected boolean keepAlive;

    public Nettyhandler(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ConnectChannel connectChannel = createConnectChannel(msg, ctx, keepAlive);
        try {
            ServerRequest serverRequest = connectChannel.read();
            serverContext.handle(serverRequest);
        } catch (Exception e) {
            // log..
            System.out.println("netty handler exception....");
            connectChannel.error(e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(this.getClass().getName());
        cause.printStackTrace();
        ctx.close();
    }
}
