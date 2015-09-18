/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty.http;

import com.twocater.diamond.core.netty.Timinghandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import com.twocater.diamond.core.netty.AbstractHandlerFactory;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author cpaladin
 */
public class HttpHandlerFactory extends AbstractHandlerFactory {

    @Override
    public final ChannelInboundHandlerAdapter createHandler() {
        return new HttpNettyHandler(serverContext, keepAlive);
    }

    @Override
    public ChannelInboundHandlerAdapter[] createDecoder() {
        return new ChannelInboundHandlerAdapter[]{new Timinghandler(), new HttpRequestDecoder(), new HttpObjectAggregator(1048576), new HttpDecoder()};
    }

    @Override
    public ChannelOutboundHandlerAdapter[] createEncoder() {
        return new ChannelOutboundHandlerAdapter[]{new HttpResponseEncoder(), new HttpEndoder()};
    }

    @Override
    public ChannelHandlerAdapter createServerHandler() {
        return new LoggingHandler(LogLevel.DEBUG);
    }

}
