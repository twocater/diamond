package com.bianfeng.dayou.accessserver;

import com.bianfeng.dayou.accessserver.server.ServerFactory;
import com.bianfeng.dayou.gameloginserver.client.request.GameLoginRequest;
import com.bianfeng.dayou.gameloginserver.client.response.GameLoginServerResponse;
import com.bianfeng.dayou.accessserver.server.GameLoginSer;
import com.twocater.diamond.core.netty.NettyHandler;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.kit.id.UuidGen;
import com.twocater.diamond.util.ToStringUtil;
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
        System.out.println("request:" + ToStringUtil.toString(request));
        // old channel
        if (channels.containsValue(ctx)) {
            ServerResponse response = dispatch(request);
            System.out.println("old channel response:" + ToStringUtil.toString(response));
            ctx.writeAndFlush(response);
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
        GameLoginRequest gameLoginRequest = ServerFactory.getInstance().getGameLoginSer().getGameLoginRequest(serverRequest);
        GameLoginServerResponse loginResult = ServerFactory.getInstance().getGameLoginSer().login(gameLoginRequest);
        if (loginResult.isSuccess()) {
            String key = loginResult.getUid() + "-" + gameLoginRequest.getGameId();
            channels.put(key, ctx);
        }
        ServerResponse serverResponse = encode(loginResult, serverRequest);
        System.out.println("new channel response:" + ToStringUtil.toString(serverResponse));
        if (loginResult.isSuccess()) {
            ctx.writeAndFlush(serverResponse);
        } else {
            ctx.writeAndFlush(serverResponse).addListener(CLOSE);
        }
    }

    private ServerResponse login(ServerRequest serverRequest) {
        GameLoginServerResponse loginResult = ServerFactory.getInstance().getGameLoginSer().login(serverRequest);
        return encode(loginResult, serverRequest);
    }

    private ServerResponse encode(GameLoginServerResponse loginResult, ServerRequest serverRequest) {
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
        switch (serverRequest.getCommand()) {
            case 1:
                return login(serverRequest);
        }
        throw new IllegalStateException("unknown command.");
//        return new ServerResponse();
    }

}