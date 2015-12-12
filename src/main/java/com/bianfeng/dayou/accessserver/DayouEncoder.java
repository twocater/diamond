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
        ServerResponse serverResponse = (ServerResponse) msg;
        out.writeByte(serverResponse.getVersion() << 4 | serverResponse.getEncrypt() << 3 | serverResponse.getLongConnection() << 2);
        out.writeByte(serverResponse.getResult());

        ByteBuf data = Unpooled.buffer();
        for (Map.Entry<String, String> entry : serverResponse.getParams().entrySet()) {
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
        if (serverResponse.getParams() != null && serverResponse.getParams().size() > 0) {
            out.writeShort(data.readableBytes());
            out.writeBytes(data);
        } else {
            out.writeShort(0);
        }
    }
}
