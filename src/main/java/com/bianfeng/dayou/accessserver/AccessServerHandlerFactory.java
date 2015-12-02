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
        return null;
    }

    @Override
    public ChannelInboundHandlerAdapter[] createDecoder() {
        return new ChannelInboundHandlerAdapter[0];
    }

    @Override
    public ChannelOutboundHandlerAdapter[] createEncoder() {
        return new ChannelOutboundHandlerAdapter[0];
    }

    @Override
    public ChannelHandlerAdapter createServerHandler() {
        return null;
    }
}
