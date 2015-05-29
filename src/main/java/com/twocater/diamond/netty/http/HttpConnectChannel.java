package com.twocater.diamond.netty.http;

import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

import com.twocater.diamond.protocol.http.HttpRequestMessage;
import com.twocater.diamond.protocol.http.HttpResponseMessage;
import com.twocater.diamond.protocol.http.HttpServerRequest;
import com.twocater.diamond.server.ConnectChannel;
import com.twocater.diamond.server.ServerRequest;

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
		channelHandlerContext.writeAndFlush(response);
		if (!keepAlive) {
			// f.addListener(ChannelFutureListener.CLOSE);
		}

	}

	@Override
	public void error(Exception e) throws Exception {
		e.printStackTrace();
		write();
	}

}
