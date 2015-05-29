package com.twocater.diamond.netty;

import com.twocater.diamond.server.ConnectChannel;

import io.netty.channel.ChannelHandlerContext;

public interface ConnectChannelFactory {
	ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive);
}
