/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.server.ServerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 *
 * @author cpaladin
 */
public interface CoderFactory {

    public ChannelInboundHandlerAdapter createHandler();

    public ChannelInboundHandlerAdapter[] createDecoder();

    public ChannelOutboundHandlerAdapter[] createEncoder();
}
