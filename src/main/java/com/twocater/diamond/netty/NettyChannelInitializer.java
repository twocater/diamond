package com.twocater.diamond.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyHandlerFactory nettyHandlerFactory;

    public NettyChannelInitializer(NettyHandlerFactory nettyHandlerFactory) {
        this.nettyHandlerFactory = nettyHandlerFactory;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        for (ChannelInboundHandlerAdapter decoder : nettyHandlerFactory.createDecoder()) {
            ch.pipeline().addLast(decoder);
        }

        for (ChannelOutboundHandlerAdapter encoder : nettyHandlerFactory.createEncoder()) {
            ch.pipeline().addLast(encoder);
        }

        ch.pipeline().addLast(nettyHandlerFactory.createHandler());

    }

}
