package com.bianfeng.dayou.accessserver;

import com.twocater.diamond.core.netty.AbstractHandlerFactory;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * Created by chenzhiwei on 15-11-30.
 */
public class AccessServerHandlerFactory extends AbstractHandlerFactory {
    @Override
    public ChannelInboundHandlerAdapter createHandler() {
        return new DayouNettyHandler(serverContext, keepAlive);
    }

    @Override
    public ChannelInboundHandlerAdapter[] createDecoder() {
        return new ChannelInboundHandlerAdapter[]{new DayouDecoder()};
    }

    @Override
    public ChannelOutboundHandlerAdapter[] createEncoder() {
        return null;
    }

    @Override
    public ChannelHandlerAdapter createServerHandler() {
        return null;
    }
}
