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

        if (byteBuf.readableBytes() <4) {
            ///
            return;
        }
        byte b1 = byteBuf.readByte();
        byte b2 = byteBuf.readByte();
        byte b3 = byteBuf.readByte();
        byte b4 = byteBuf.readByte();

    }
}
