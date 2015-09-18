/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty.http;

import com.twocater.diamond.core.protocol.http.HttpRequestMessage;
import com.twocater.diamond.util.LoggerConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cpaladin
 */
public class HttpDecoder extends ChannelInboundHandlerAdapter {
    private static final Logger debug = LoggerFactory.getLogger(LoggerConstant.DEBUG);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerConstant.nettyHandlerLog.debug("{}", new Object[]{ctx.channel()});

        FullHttpRequest request = (FullHttpRequest) msg;

        try {
            if (request.getDecoderResult().isFailure()) {
                debug.debug("decoderfail:{}", request);
                ctx.close();
                return;
            }

//        LoggingHandler


            HttpHeaders headers = request.headers();
//        StringBuilder buf = new StringBuilder();
//        if (!headers.isEmpty()) {
//            for (Map.Entry<String, String> h : headers) {
//                String key = h.getKey();
//                String value = h.getValue();
//                buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
//            }
//            buf.append("\r\n");
//        }
//        System.out.println(buf.toString());

            // 获取头部
            Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
            for (String name : headers.names()) {
                headersMap.put(name, headers.getAll(name));
            }

            // 获取数据
            byte[] data = null;
            int n = request.content().readableBytes();
            if (n > 0) {
                data = new byte[n];
                request.content().readBytes(data);
            }

            HttpRequestMessage httpRequestMessage = new HttpRequestMessage(
                    request.getMethod(), request.getProtocolVersion(), request.getUri(), headersMap, data);

            ctx.fireChannelRead(httpRequestMessage);

        } finally {
            // 这个方法需要调用吗？确认下，不调用netty会打印警告日志！
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 此类抛出异常时，方法被调用了两次？？！！！
        LoggerConstant.errorLog.error("decode exception:", cause);
        cause.printStackTrace();
        ctx.close();
    }
}
