package com.twocater.diamond.core.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final CoderFactory coderFactory;
    private final int timeout;
    private EventExecutorGroup eventExecutorGroup;

    public NettyChannelInitializer(CoderFactory coderFactory, int timeout, int bussThread) {
        this.coderFactory = coderFactory;
        this.timeout = timeout;
        if (bussThread > 0) {
            eventExecutorGroup = new DefaultEventExecutorGroup(bussThread);
        }
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        for (ChannelInboundHandlerAdapter decoder : coderFactory.createDecoder()) {
            ch.pipeline().addLast(decoder);
        }

        if (timeout > 0) {
            ReadTimeoutHandler readTimeoutHandler = new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS);
            ch.pipeline().addLast(readTimeoutHandler);
        }

        ChannelOutboundHandlerAdapter[] encoders = coderFactory.createEncoder();
        if (encoders != null) {
            for (ChannelOutboundHandlerAdapter encoder : encoders) {
                ch.pipeline().addLast(encoder);
            }
        }


        ch.pipeline().addLast(eventExecutorGroup, coderFactory.createHandler());


    }
}
