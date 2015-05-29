/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.netty.CoderFactory;
import com.twocater.diamond.server.ServerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 *
 * @author cpaladin
 */
@Deprecated
public class HttpCoderFactory implements CoderFactory {

	private final ServerContext serverContext;

	public HttpCoderFactory(ServerContext serverContext) {
		this.serverContext = serverContext;
	}

	@Override
	public final ChannelInboundHandlerAdapter createHandler() {
		return new HttpNettyHandler(serverContext);
	}

	@Override
	public ChannelInboundHandlerAdapter[] createDecoder() {
		return new ChannelInboundHandlerAdapter[] { new HttpRequestDecoder(), new HttpDecoder() };
	}

	@Override
	public ChannelOutboundHandlerAdapter[] createEncoder() {
		return new ChannelOutboundHandlerAdapter[] { new HttpResponseEncoder(), new HttpEndoder() };
	}

}
