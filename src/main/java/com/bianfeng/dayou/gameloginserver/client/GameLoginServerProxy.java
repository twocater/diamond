package com.bianfeng.dayou.gameloginserver.client;

import com.bianfeng.dayou.gameloginserver.client.request.GameLoginRequest;
import com.bianfeng.dayou.gameloginserver.client.response.GameLoginServerResponse;

/**
 * Created by chenzhiwei on 15-12-9.
 * <p/>
 * proxy of game login server
 */
public class GameLoginServerProxy {
    public static final int SUCCESS = 1;

    private String ip;
    private int port;
    private int timeout;

    public GameLoginServerProxy(String ip, int port, int timeout) {
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
    }

    public GameLoginServerResponse gameLogin(GameLoginRequest gameLoginRequest) {
        GameLoginServerResponse response = new GameLoginServerResponse();

        response.setUid(Long.parseLong(gameLoginRequest.getUserName()));
        response.setUserName(gameLoginRequest.getUserName());
        response.setResult(SUCCESS);
        response.setSuccess(true);
        // 金币数
        // ...其他信息
        return response;

    }

}
