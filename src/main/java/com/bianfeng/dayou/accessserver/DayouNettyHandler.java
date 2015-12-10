package com.bianfeng.dayou.accessserver;

import com.bianfeng.dayou.loginserver.LoginResponse;
import com.bianfeng.dayou.accessserver.server.LoginServer;
import com.twocater.diamond.core.netty.NettyHandler;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.kit.id.UuidGen;
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
        ServerRequest request = (ServerRequest) msg;
        // old channel
        if (channels.containsValue(ctx.channel())) {

        } else { // new channel
            if (request.getLongConnection() == 1) { //　long connection
                // login auth
                login(ctx, request);
            } else { // 客户端短连接
                ServerResponse response = dispatch(request);
                ctx.writeAndFlush(response).addListener(CLOSE);
            }
        }
    }

    private void login(ChannelHandlerContext ctx, ServerRequest serverRequest) {
        LoginResponse loginResult = LoginServer.login(serverRequest.getRawMessage());
        if (loginResult.isSuccess()) {
            channels.put(uuidGen.createId(), ctx);
        }
        ServerResponse serverResponse = encode(loginResult, serverRequest);
        if (loginResult.isSuccess()) {
            ctx.writeAndFlush(serverResponse);
        } else {
            ctx.writeAndFlush(serverResponse).addListener(CLOSE);
        }
    }

    private ServerResponse login(ServerRequest serverRequest) {
        LoginResponse loginResult = LoginServer.login(serverRequest.getRawMessage());
        return encode(loginResult, serverRequest);
    }

    private ServerResponse encode(LoginResponse loginResult, ServerRequest serverRequest) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setResult((byte) loginResult.getResult());
        serverResponse.setEncrypt(serverRequest.getEncrypt());
        serverResponse.setLongConnection(serverRequest.getLongConnection());
        serverResponse.setVersion(serverRequest.getVersion());
        serverResponse.setParams("u", loginResult.getUserName());
        serverResponse.setParams("n", loginResult.getNickName());

        return serverResponse;
    }

    private ServerResponse dispatch(ServerRequest serverRequest) {
        System.out.println(serverRequest);
        switch (serverRequest.getCommand()) {
            case 1:
                return login(serverRequest);
        }
//        throw new IllegalStateException("unknown command.");
        return new ServerResponse();
    }

}