package com.bianfeng.dayou.accessserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by chenzhiwei on 15-11-30.
 */
public class DayouDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            ///
            return;
        }
        int b1 = byteBuf.readByte();
        int b2 = byteBuf.readByte();
        short command = byteBuf.readShort();

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(command);


    }
}
