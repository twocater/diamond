package com.twocater.diamond.core.netty;

import com.twocater.diamond.util.LoggerConstant;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * Created by chenzhiwei on 15-9-18.
 */
public class Timinghandler extends ChannelDuplexHandler {
    private long start;
    private long end;

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LoggerConstant.timingLog.debug("channelRegistered");

        super.channelRegistered(ctx);
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LoggerConstant.timingLog.debug("channelUnregistered");

        super.channelUnregistered(ctx);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoggerConstant.timingLog.debug("channelActive");

        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LoggerConstant.timingLog.debug("channelInactive");

        super.channelInactive(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LoggerConstant.timingLog.debug("exceptionCaught");

        super.exceptionCaught(ctx, cause);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LoggerConstant.timingLog.debug("userEventTriggered");

        super.userEventTriggered(ctx, evt);
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("bind");

        super.bind(ctx, localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("connect");

        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("disconnect");

        super.disconnect(ctx, promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("close");

        super.close(ctx, promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("deregister");

        super.deregister(ctx, promise);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        start = System.currentTimeMillis();
        LoggerConstant.timingLog.debug("channelRead");

        ctx.fireChannelRead(msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LoggerConstant.timingLog.debug("write");

        ctx.write(msg, promise);
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        LoggerConstant.timingLog.info("cost:{}", new Object[]{System.currentTimeMillis() - start});
        LoggerConstant.timingLog.debug("flush");

        ctx.flush();
    }

}
