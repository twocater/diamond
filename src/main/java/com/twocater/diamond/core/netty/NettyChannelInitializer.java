package com.twocater.diamond.core.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final CoderFactory coderFactory;

    public NettyChannelInitializer(CoderFactory coderFactory) {
        this.coderFactory = coderFactory;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        for (ChannelInboundHandlerAdapter decoder : coderFactory.createDecoder()) {
            ch.pipeline().addLast(decoder);
        }

        for (ChannelOutboundHandlerAdapter encoder : coderFactory.createEncoder()) {
            ch.pipeline().addLast(encoder);
        }

        ch.pipeline().addLast(coderFactory.createHandler());


    }
}
