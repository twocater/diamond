package com.twocater.diamond.netty.http;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.twocater.diamond.protocol.http.HttpResponseMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 *
 * @author cpaladin
 */
public class HttpEndoder extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

		HttpResponseMessage httpResponseMessage = (HttpResponseMessage) msg;

		// 体信息
		byte[] data = httpResponseMessage.getData();
		FullHttpResponse response = null;
		if (data == null) {
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT, Unpooled.EMPTY_BUFFER, false);

		} else {
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(data), false);
		}

		// 额外信息
		// HttpStatus status = httpResponseMessage.getStatus();
		// if (status != null) {
		// httpResponse.setStatus(org.jboss.netty.handler.codec.http.HttpResponseStatus.valueOf(status.getStatus()));
		// }
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

		// response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
		// response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
		// response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

		ctx.write(response);
	}
}
