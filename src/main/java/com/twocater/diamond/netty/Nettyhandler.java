/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.server.ConnectChannel;
import com.twocater.diamond.server.ServerContext;
import com.twocater.diamond.server.ServerRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author cpaladin
 */
public abstract class Nettyhandler extends ChannelInboundHandlerAdapter implements ConnectChannelFactory {

	// private final Server server;
	private final ServerContext serverContext;

	protected boolean keepAlive;

	public Nettyhandler(ServerContext serverContext) {
		this.serverContext = serverContext;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ConnectChannel connectChannel = createConnectChannel(msg, ctx, keepAlive);
		try {
			ServerRequest serverRequest = connectChannel.read();
			serverContext.handle(serverRequest);
		} catch (Exception e) {
			// log..
			connectChannel.error(e);
		}
	}
}
