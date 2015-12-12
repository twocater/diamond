package com.bianfeng.dayou.accessserver.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class AccessServerCodec {

    public static byte[] encode(AccessServerRequest request) {

        ByteBuf byteBuf = Unpooled.buffer(4); // 预分配的可大一点
        byteBuf.writeByte(request.getVersion() << 4 | request.getEncrypt() << 3 | request.getLongConnection() << 2);
        byteBuf.writeByte(request.getCommand());

        ByteBuf data = Unpooled.buffer();
        for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
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
        if (request.getParams() != null && request.getParams().size() > 0) {
            byteBuf.writeShort(data.readableBytes());
            byteBuf.writeBytes(data);
        } else {
            byteBuf.writeShort(0);
        }

        byte[] encodeBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(encodeBytes);
        return encodeBytes;
    }


    public static AccessServerResponse decode(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);

        AccessServerResponse response = new AccessServerResponse();
        byte b1 = byteBuf.readByte();
        byte result = byteBuf.readByte();
        int length = byteBuf.readShort();

        response.setVersion((byte) ((b1 & 0xf0) >> 4));
        response.setEncrypt((byte) ((b1 & 0x08) >> 3));
        response.setLongConnection((byte) ((b1 & 0x04) >> 2));
        response.setResult(result);

        while (length >= 4) {
            short keyLen = byteBuf.readShort();
            length -= 2;
            String key = null;
            String value = null;
            if (keyLen > 0) {
                key = byteBuf.readBytes(keyLen).toString(StandardCharsets.UTF_8);
            }
            length -= keyLen;
            short valueLen = byteBuf.readShort();
            length -= 2;
            if (keyLen > 0) {
                value = byteBuf.readBytes(valueLen).toString(StandardCharsets.UTF_8);
            }
            length -= valueLen;
            response.setParam(key, value);
        }
        if (length != 0) {
            throw new IllegalStateException("decode error.");
        }

        return response;
    }
}
