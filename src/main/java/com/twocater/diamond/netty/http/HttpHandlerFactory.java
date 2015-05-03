/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.netty.NettyChannelInitializer;
import com.twocater.diamond.netty.NettyHandlerFactory;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 *
 * @author cpaladin
 */
public class HttpHandlerFactory implements NettyHandlerFactory {

    @Override
    public ChannelInboundHandlerAdapter createHandler() {
        return new HttpHandler();
    }

    @Override
    public ChannelInitializer createChildHandler() {
        return new NettyChannelInitializer(this);
    }

    @Override
    public ChannelInboundHandlerAdapter[] createDecoder() {
        return new ChannelInboundHandlerAdapter[]{new HttpRequestDecoder(), new HttpObjectAggregator(1048576), new ToHttpPlainRequestHandler()};
    }

    @Override
    public ChannelOutboundHandlerAdapter[] createEncoder() {
        return new ChannelOutboundHandlerAdapter[]{new HttpResponseEncoder()};
    }

}
