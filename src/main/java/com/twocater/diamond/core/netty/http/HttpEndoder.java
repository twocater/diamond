package com.twocater.diamond.core.netty.http;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.twocater.diamond.core.protocol.http.HttpResponseMessage;

import com.twocater.diamond.util.LoggerConstant;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

/**
 * @author cpaladin
 */
public class HttpEndoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LoggerConstant.nettyHandlerLog.debug("{}", new Object[]{ctx.channel().toString()});

        HttpResponseMessage httpResponseMessage = (HttpResponseMessage) msg;

        // 体信息
        byte[] data = httpResponseMessage.getData();
        FullHttpResponse response = null;
        if (data == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.EMPTY_BUFFER, false);

        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(data), false);
        }

        // 额外信息
        // http 响应状态码
        HttpResponseStatus status = httpResponseMessage.getStatus();
        if (status != null) {
            response.setStatus(status);
        }
        // http 协议版本
        HttpVersion protocolVersion = httpResponseMessage.getProtocolVersion();
        if (protocolVersion != null) {
            response.setProtocolVersion(httpResponseMessage.getProtocolVersion());
        }

        // 头信息
        Map<String, List<String>> headers = httpResponseMessage.getHeaders();
        if (headers != null && headers.size() > 0) {
            for (Entry<String, List<String>> entry : headers.entrySet()) {
                response.headers().set(entry.getKey(), entry.getValue());
            }
        }

        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        boolean keepAlive = httpResponseMessage.isKeepAlive();
        response.headers().set(HttpHeaders.Names.CONNECTION, keepAlive ? HttpHeaders.Values.KEEP_ALIVE : HttpHeaders.Values.CLOSE);
        if (!keepAlive) {
            ctx.write(response).addListener(CLOSE);
        } else {
            ctx.write(response);
        }
    }

    private static ChannelFutureListener CLOSE = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            channelFuture.channel().close();
        }
    };
}
