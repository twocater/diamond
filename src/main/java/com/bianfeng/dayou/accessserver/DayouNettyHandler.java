package com.bianfeng.dayou.accessserver;

import com.twocater.diamond.core.netty.NettyHandler;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2015/12/6.
 */
public class DayouNettyHandler extends NettyHandler {
    public DayouNettyHandler(ServerContext serverContext, boolean keepAlive) {
        super(serverContext, keepAlive);
    }

    @Override
    public ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
        return null;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg);
    }
}
