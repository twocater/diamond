/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 *
 * @author cpaladin
 *
 * Create handlers.
 */
public interface NettyHandlerFactory extends HandlerFactory {

    ChannelInboundHandlerAdapter createHandler();

    ChannelInitializer createChildHandler();

    ChannelInboundHandlerAdapter[] createDecoder();

    ChannelOutboundHandlerAdapter[] createEncoder();

}
