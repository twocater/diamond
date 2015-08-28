package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.ConnectChannel;

import io.netty.channel.ChannelHandlerContext;

public interface ConnectChannelFactory {
	ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive);
}
