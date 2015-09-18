package com.twocater.diamond.core.netty.http;

import com.twocater.diamond.core.server.NotExistException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

import com.twocater.diamond.core.protocol.http.HttpRequestMessage;
import com.twocater.diamond.core.protocol.http.HttpResponseMessage;
import com.twocater.diamond.core.protocol.http.HttpServerRequest;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpConnectChannel implements ConnectChannel {

    private HttpRequestMessage message;
    private ChannelHandlerContext channelHandlerContext;
    private boolean keepAlive;

    public HttpConnectChannel(HttpRequestMessage message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
        this.message = message;
        this.channelHandlerContext = channelHandlerContext;
        this.keepAlive = keepAlive;
    }

    @Override
    public SocketAddress getLocalAddress() {
        return channelHandlerContext.channel().localAddress();
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return channelHandlerContext.channel().remoteAddress();
    }

    @Override
    public ServerRequest read() throws Exception {
        return new HttpServerRequest(this, message);
    }

    @Override
    public void write() throws Exception {
        HttpResponseMessage response = message.getResponse();
        boolean keepAlive = response.isKeepAlive();
        // 如果服务器不支持长连接，修改response的keepalive为false

        if (keepAlive && !this.keepAlive) {
            keepAlive = false;
            response.setKeepAlive(keepAlive);
        }
        channelHandlerContext.write(response);
    }

    @Override
    public void error(Exception e) throws Exception {
        if (e instanceof NotExistException) {
            message.getResponse().setStatus(HttpResponseStatus.NOT_FOUND);
        } else {
            message.getResponse().setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        write();

    }

}
