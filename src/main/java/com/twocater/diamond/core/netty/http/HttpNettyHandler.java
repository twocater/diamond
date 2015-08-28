package com.twocater.diamond.core.netty.http;

import io.netty.channel.ChannelHandlerContext;

import com.twocater.diamond.core.netty.Nettyhandler;
import com.twocater.diamond.core.protocol.http.HttpRequestMessage;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;

public class HttpNettyHandler extends Nettyhandler {

	public HttpNettyHandler(ServerContext serverContext) {
		super(serverContext);
	}

	@Override
	public ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
		return new HttpConnectChannel((HttpRequestMessage) message, channelHandlerContext, keepAlive);
	}

}
