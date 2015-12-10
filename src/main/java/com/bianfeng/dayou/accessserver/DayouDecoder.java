package com.bianfeng.dayou.accessserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

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

        byte b1 = frame.readByte();
        byte command = frame.readByte();
        int length = frame.readShort();

        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setVersion((byte) ((b1 & 0xf0) >> 4));
        serverRequest.setEncrypt((byte) ((b1 & 0x08) >> 3));
        serverRequest.setLongConnection((byte) ((b1 & 0x04) >> 2));
        serverRequest.setCommand(command);
        serverRequest.setData(frame.readBytes(length).array());
        return serverRequest;
    }
}
