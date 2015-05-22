/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.netty.AbstractHandlerFactory;
import com.twocater.diamond.netty.CoderFactory;
import com.twocater.diamond.netty.NettyChannelInitializer;
import com.twocater.diamond.netty.NettyHandlerFactory;
import com.twocater.diamond.netty.Nettyhandler;
import com.twocater.diamond.server.ServerContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 *
 * @author cpaladin
 */
public class HttpHandlerFactory extends AbstractHandlerFactory {

    @Override
    public final ChannelInboundHandlerAdapter createHandler() {
        return new Nettyhandler(serverContext);
    }

    @Override
    public ChannelInboundHandlerAdapter[] createDecoder() {
        return new ChannelInboundHandlerAdapter[]{new HttpRequestDecoder(), new HttpDecoder()};
    }

    @Override
    public ChannelOutboundHandlerAdapter[] createEncoder() {
        return new ChannelOutboundHandlerAdapter[]{new HttpResponseEncoder(), new HttpEndoder()};
    }

}
