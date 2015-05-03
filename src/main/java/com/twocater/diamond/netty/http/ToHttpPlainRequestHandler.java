package com.twocater.diamond.netty.http;

import com.twocater.diamond.api.service.HttpPlainRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class ToHttpPlainRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest httpRequest = (HttpRequest) msg;
        System.out.println(httpRequest.getUri());

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.getUri());

        HttpPlainRequest httpPlainRequest = new HttpPlainRequest();

        ctx.fireChannelRead(httpPlainRequest);
    }

}
