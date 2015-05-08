/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.protocol.http.HttpRequestMessage;
import com.twocater.diamond.protocol.http.HttpResponseMessage;
import com.twocater.diamond.protocol.http.HttpServerRequest;
import com.twocater.diamond.server.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author cpaladin
 */
public class Nettyhandler extends ChannelInboundHandlerAdapter {

    private final Server server;

    public Nettyhandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequestMessage httpRequestMessage = (HttpRequestMessage) msg;
        HttpResponseMessage httpResponseMessage = httpRequestMessage.getResponse();
        
        HttpServerRequest serverRequest = new HttpServerRequest(httpRequestMessage);
        
        
        server.handle(serverRequest);
        ctx.writeAndFlush(httpResponseMessage);
    }
}
