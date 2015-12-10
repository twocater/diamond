package com.bianfeng.dayou.accessserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/8.
 */
public class DayouEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ServerRequest serverRequest = (ServerRequest) msg;
        out.writeByte(serverRequest.getVersion() << 4 | serverRequest.getEncrypt() << 3 | serverRequest.getLongConnection() << 2);
        out.writeByte(serverRequest.getCommand());

        ByteBuf data = Unpooled.buffer();
        for (Map.Entry<String,String> entry:serverRequest.getParams().entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                byte[] key = entry.getKey().getBytes(StandardCharsets.UTF_8);
                // 这里要判断key的长度是否溢出
                data.writeShort(key.length);
                data.writeBytes(key);
                byte[] value = entry.getValue().getBytes(StandardCharsets.UTF_8);
                data.writeShort(value.length);
                data.writeBytes(value);
            }
        }
        if (serverRequest.getParams() != null && serverRequest.getParams().size() > 0) {
            out.writeShort(data.readableBytes());
            out.writeBytes(data);
        } else {
            out.writeShort(0);
        }
    }
}
