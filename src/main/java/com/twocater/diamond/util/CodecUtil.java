package com.twocater.diamond.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/13.
 */
public class CodecUtil {

    public static byte[] encodeFromMap(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return new byte[0];
        }
        ByteBuf data = Unpooled.buffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
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
        byte[] bytes = new byte[data.readableBytes()];
        data.readBytes(bytes);
        return bytes;

    }

    public static Map<String, String> decodeToMap(byte[] data) {
        if (data == null || data.length == 0) {
            return Collections.emptyMap();
        }
        if (data.length < 4) {
            throw new IllegalStateException("data length less than 4.");
        }

        return decodeToMap(Unpooled.wrappedBuffer(data));
    }

    public static Map<String, String> decodeToMap(ByteBuf byteBuf) {
        int length;
        if (byteBuf == null || (length = byteBuf.readableBytes()) == 0) {
            return null;
        }
        if (length < 4) {
            throw new IllegalStateException("data length less than 4.");
        }

        Map<String, String> map = new HashMap<String, String>();
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
            map.put(key, value);
        }
        if (length != 0) {
            throw new IllegalStateException("decode error.extra data of length=" + length);
        }
        return map;
    }
}
