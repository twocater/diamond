package com.twocater.diamond.core.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;

/**
 *
 * @author cpaladin
 *
 * Create handlers.
 */
public interface NettyHandlerFactory extends HandlerFactory {

    public ChannelHandlerAdapter createServerHandler();

    public ChannelInitializer createChildHandler(int timeout);

}
