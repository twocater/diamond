/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.core.server.ServerRequest;

import com.twocater.diamond.util.ExceptionUtil;
import com.twocater.diamond.util.LoggerConstant;
import io.netty.channel.*;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cpaladin
 */
public abstract class NettyHandler extends ChannelInboundHandlerAdapter implements ConnectChannelFactory {
    // private final Server server;
    private final ServerContext serverContext;

    protected boolean keepAlive;

    public NettyHandler(ServerContext serverContext, boolean keepAlive) {
        this.serverContext = serverContext;
        this.keepAlive = keepAlive;
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        // 从调试过程中看，调用过flush后，当走完outBound处理器后，就会将数据写到实际的通道中，也就是说，我已经读完数据了(走完inBound处理器了)，只要我写完，你就可以发数据了
//        // 需要从源码中确认下
//        ctx.flush();
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerConstant.nettyHandlerLog.debug("{}", new Object[]{ctx.channel()});

        ConnectChannel connectChannel = createConnectChannel(msg, ctx, keepAlive);
        try {
            ServerRequest serverRequest = connectChannel.read();
            serverContext.handle(serverRequest);
        } catch (Exception e) {
            serverContext.getLog().error("channelRead error.{}", ExceptionUtil.getExceptionInfo(e));
            connectChannel.error(e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        try {
            if (cause instanceof ReadTimeoutException) {
                LoggerConstant.timeoutLog.error("timeout:{},{}", new Object[]{ctx.channel().toString(), ExceptionUtil.getExceptionInfo(cause)});
            } else {
                serverContext.getLog().error("exceptionCaught:{},{}", new Object[]{ctx.channel().toString(), ExceptionUtil.getExceptionInfo(cause)});
            }
            ctx.close();
        } catch (Exception e) {
            serverContext.getLog().error("exceptionUnknown:{},{}", new Object[]{ctx.channel().toString(), ExceptionUtil.getExceptionInfo(cause)});
        }
    }

    public static ChannelFutureListener CLOSE = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            channelFuture.channel().close();
        }
    };
}
