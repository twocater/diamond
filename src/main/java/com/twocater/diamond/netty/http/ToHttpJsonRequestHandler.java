/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.api.service.HttpJsonRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 *
 * @author cpaladin
 */
public class ToHttpJsonRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest httpRequest = (HttpRequest) msg;
        System.out.println(httpRequest.getUri());

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.getUri());

        HttpJsonRequest httpJsonRequest = new HttpJsonRequest();
        ctx.fireChannelRead(httpJsonRequest);
    }
}
