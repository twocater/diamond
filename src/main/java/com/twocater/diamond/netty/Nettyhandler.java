/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.server.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author cpaladin
 */
public class Nettyhandler extends ChannelInboundHandlerAdapter {

//    Server server;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        server.handle(msg);
    }

}
