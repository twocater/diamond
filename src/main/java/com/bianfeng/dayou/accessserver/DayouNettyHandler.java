package com.bianfeng.dayou.accessserver;

import com.bianfeng.dayou.loginserver.LoginResult;
import com.bianfeng.dayou.accessserver.server.LoginServer;
import com.twocater.diamond.core.netty.NettyHandler;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.kit.id.UuidGen;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/6.
 */
public class DayouNettyHandler extends NettyHandler {
    private static Map<String, ChannelHandlerContext> channels = new HashMap<String, ChannelHandlerContext>();
    private static final UuidGen uuidGen = new UuidGen();


    public DayouNettyHandler(ServerContext serverContext, boolean keepAlive) {
        super(serverContext, keepAlive);
    }

    @Override
    public ConnectChannel createConnectChannel(Object message, ChannelHandlerContext channelHandlerContext, boolean keepAlive) {
        return null;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage = (NettyMessage) msg;
        System.out.println(ctx);
        // old channel
        if (channels.containsValue(ctx.channel())) {

        } else { // new channel
            if (nettyMessage.getLongConnection() == 1) { //　long connection
                // auth user
                LoginResult loginResult = LoginServer.login(nettyMessage.getMessage());
                if (loginResult.isSuccess()) {
                    channels.put(uuidGen.createId(), ctx);
                    NettyMessage response = encode(nettyMessage, loginResult);
                    ctx.writeAndFlush(response);
                } else {
                    // auth fail, close channel
                    ctx.close();
                }

            } else { // 客户端短连接
                ctx.writeAndFlush(msg);
            }
        }

        System.out.println(ctx.channel().toString());
    }

    private NettyMessage encode(NettyMessage request, LoginResult loginResult) {
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.setCommand(request.getCommand());
        nettyMessage.setEncrypt(request.getEncrypt());
        nettyMessage.setLongConnection(request.getLongConnection());
        nettyMessage.setVersion(request.getVersion());

        return nettyMessage;
    }

}
