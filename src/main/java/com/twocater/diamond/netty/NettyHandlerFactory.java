/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.server.ServerContext;
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

    public ChannelInitializer createChildHandler();

}
