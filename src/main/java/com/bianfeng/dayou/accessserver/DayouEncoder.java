package com.bianfeng.dayou.accessserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Administrator on 2015/12/8.
 */
public class DayouEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;
        out.writeByte(nettyMessage.getVersion() << 4 | nettyMessage.getEncrypt() << 3);
        out.writeByte(nettyMessage.getCommand());
        out.writeShort(nettyMessage.getContent().length);
        out.writeBytes(nettyMessage.getContent());
    }
}
