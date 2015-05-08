/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.protocol.http.HttpRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class HttpDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg);
        FullHttpRequest request = (FullHttpRequest) msg;
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        HttpHeaders headers = request.headers();
        StringBuilder buf = new StringBuilder();
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> h : headers) {
                String key = h.getKey();
                String value = h.getValue();
                buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
            }
            buf.append("\r\n");
        }
        System.out.println(buf.toString());

        Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
        for (String name : headers.names()) {
            headersMap.put(name, headers.getAll(name));
        }

        byte[] data = null;
        if (request.content().hasArray()) {
            data = request.content().array();
        }

        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(
                request.getMethod(), request.getProtocolVersion(), request.getUri(), headersMap, data);
        ctx.fireChannelRead(httpRequestMessage);
    }
}
