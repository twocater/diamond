package com.bianfeng.dayou.accessserver;

import com.bianfeng.dayou.accessserver.client.util.LoggerConstants;
import com.bianfeng.dayou.accessserver.server.ServerFactory;
import com.bianfeng.dayou.gameloginserver.client.request.GameLoginRequest;
import com.bianfeng.dayou.gameloginserver.client.response.GameLoginServerResponse;
import com.bianfeng.dayou.accessserver.server.GameLoginSer;
import com.twocater.diamond.core.netty.NettyHandler;
import com.twocater.diamond.core.server.ConnectChannel;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.kit.id.UuidGen;
import com.twocater.diamond.util.ToStringUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
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
        if (channels.containsValue(ctx)) { // 已登录玩家发送消息
            ServerResponse response = dispatchLongConnection(request);
            System.out.println("old channel response:" + ToStringUtil.toString(response));
            ctx.writeAndFlush(response).addListener(channelFutureListener);
        } else {
            if (request.getLongConnection() == 1) { //　长连接只用于玩家登录
                login(ctx, request);
            } else { // 短连接
                ServerResponse response = dispatchShortConnection(request);
                ctx.writeAndFlush(response).addListener(CLOSE);
            }
        }
    }

    private ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            System.out.println("write finish.");
        }
    };

    private void login(final ChannelHandlerContext ctx, ServerRequest serverRequest) {
        GameLoginRequest gameLoginRequest = ServerFactory.getInstance().getGameLoginSer().getGameLoginRequest(serverRequest);
        GameLoginServerResponse loginResult = ServerFactory.getInstance().getGameLoginSer().login(gameLoginRequest);
        if (loginResult.isSuccess()) {
            String key = loginResult.getUid() + "-" + gameLoginRequest.getGameId();
            channels.put(key, ctx);
        }
        final ServerResponse serverResponse = encode(loginResult, serverRequest);
        System.out.println("new channel response:" + ToStringUtil.toString(serverResponse));
        if (loginResult.isSuccess()) {
            ctx.writeAndFlush(serverResponse).addListener(channelFutureListener);
        } else {
            ctx.writeAndFlush(serverResponse).addListener(CLOSE);
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    ctx.writeAndFlush(serverResponse).addListener(channelFutureListener);
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    private ServerResponse login(ServerRequest serverRequest) {
        GameLoginServerResponse loginResult = ServerFactory.getInstance().getGameLoginSer().login(serverRequest);
        return encode(loginResult, serverRequest);
    }

    /**
     * encode  GameLoginServerResponse to ServerResponse
     *
     * @param loginResult
     * @param serverRequest
     * @return
     */
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

    private ServerResponse dispatchLongConnection(ServerRequest serverRequest) {
        switch (serverRequest.getCommand()) {
            case 1: // 游戏命令
                ServerFactory.getInstance().getGameLogicSer().handle(serverRequest);
                break;
        }
        throw new IllegalArgumentException("unknown command.");
    }

    private ServerResponse dispatchShortConnection(ServerRequest serverRequest) {
        switch (serverRequest.getCommand()) {
            case 2: // 服务器同步命令
                return sync(serverRequest);
        }

        throw new IllegalArgumentException("unknown command.");
    }

    public interface ResultCodeConstants {
        public static final byte SUCCESS = 0x01;
        public static final byte CHANNEL_ID_NOT_EXISTS = 0x02;
    }

    /**
     * 同步消息给客户端
     *
     * @param serverRequest
     * @return
     */
    private ServerResponse sync(ServerRequest serverRequest) {
        final String channelId = serverRequest.getParam("channelId");
        ChannelHandlerContext channelHandlerContext = channels.get(channelId);
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setResult(ResultCodeConstants.SUCCESS);
        if (channelHandlerContext != null) {
            // 构造response对象给客户端
            final ServerResponse responseToClient = new ServerResponse();
//            responseToClient.setLongConnection((byte) 1);
            responseToClient.setResult(serverRequest.getCommand());
//            responseToClient.setEncrypt();
//            serverResponse.setVersion();
            responseToClient.setParams(serverRequest.getParams());
            channelHandlerContext.writeAndFlush(responseToClient).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    LoggerConstants.serverLog.debug("responseToclient:channel id null.");
                }
            });
        } else {
            serverResponse.setResult(ResultCodeConstants.CHANNEL_ID_NOT_EXISTS);
            LoggerConstants.serverLog.debug("responseToclient:{},{}", new Object[]{channelId, null});
        }
        return serverResponse;
    }

}