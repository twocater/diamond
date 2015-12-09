package com.bianfeng.dayou.accessserver.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by chenzhiwei on 15-12-2.
 */
public class Client {

    public static void main(String[] args) {

        try {
            LongSocketIO longSocketIO = new LongSocketIO("localhost", 8888, 6000);

            byte[] content = "hellllll".getBytes("utf-8");

            ByteBuf byteBuf = Unpooled.buffer(4);
            // 00021100
            byteBuf.writeByte(0x2c);
            // cmd:00000010
            byteBuf.writeByte(0x02);
            // length:
            byteBuf.writeShort(content.length);

            byteBuf.writeBytes(content);
            longSocketIO.write(byteBuf.array());
            longSocketIO.flush();

            longSocketIO.write(byteBuf.array());
            longSocketIO.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] shortToBytes(int i) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (i & 0xFF);
        bytes[0] = (byte) (i >> 8 & 0xFF);
        return bytes;
    }

    public static int bytesToShort(byte[] bytes) {
        int b0 = bytes[0] & 0xff;
        int b1 = bytes[1] & 0xff;
        return b0 << 8 | b1;
    }
}
