package com.bianfeng.dayou.accessserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by chenzhiwei on 15-11-30.
 */
public class DayouDecoder extends LengthFieldBasedFrameDecoder {

    public DayouDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        int b1 = frame.readByte();
        int command = frame.readByte();
        int length = frame.readShort();

        System.out.println(b1);
        System.out.println(command);
        System.out.println(length);
        System.out.println(frame.readBytes(length).toString(StandardCharsets.UTF_8));
        return new NettyMessage();
    }

    //    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//        if (byteBuf.readableBytes() < 4) {
//            ///
//            return;
//        }
//        int b1 = byteBuf.readByte();
//        int command = byteBuf.readByte();
//        int length = byteBuf.readShort();
//
//        System.out.println(b1);
//        System.out.println(command);
//        System.out.println(length);
//        if (byteBuf.readableBytes() < length) {
//            return;
//        }
//        ByteBuf content = byteBuf.readBytes(length);
//        System.out.println(content.toString(StandardCharsets.UTF_8));
//
//    }
}
