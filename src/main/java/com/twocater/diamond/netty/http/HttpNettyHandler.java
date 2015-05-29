package com.twocater.diamond.netty.http;

import io.netty.channel.ChannelHandlerContext;

import com.twocater.diamond.netty.Nettyhandler;
import com.twocater.diamond.protocol.http.HttpRequestMessage;
import com.twocater.diamond.server.ConnectChannel;
import com.twocater.diamond.server.ServerContext;

public class HttpNettyHandler extends Nettyhandler {

	public HttpNettyHandler(ServerContext serverContext) {
		super(serverContext);
	}

	@Override
	public ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
		return new HttpConnectChannel((HttpRequestMessage) message, channelHandlerContext, keepAlive);
	}

}
