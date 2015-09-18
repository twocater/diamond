package com.twocater.diamond.core.netty.http;

import com.twocater.diamond.core.netty.NettyHandler;
import io.netty.channel.ChannelHandlerContext;

import com.twocater.diamond.core.protocol.http.HttpRequestMessage;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;

public class HttpNettyHandler extends NettyHandler {

	public HttpNettyHandler(ServerContext serverContext, boolean keepAlive) {
		super(serverContext, keepAlive);
	}

	@Override
	public ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
		return new HttpConnectChannel((HttpRequestMessage) message, channelHandlerContext, keepAlive);
	}

}
