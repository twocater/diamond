package com.bianfeng.dayou.accessserver.server;

import com.bianfeng.dayou.accessserver.ServerRequest;
import com.bianfeng.dayou.gameloginserver.client.GameLoginServerProxy;
import com.bianfeng.dayou.gameloginserver.client.request.GameLoginRequest;
import com.bianfeng.dayou.gameloginserver.client.response.GameLoginServerResponse;
import com.twocater.diamond.util.ObjectUtil;

/**
 * Created by chenzhiwei on 15-12-9.
 */
public class GameLoginSer {

    private GameLoginServerProxy gameLoginServerProxy;

    public GameLoginSer() {
        gameLoginServerProxy = new GameLoginServerProxy("localhost", 9000, 6000);
    }


    public GameLoginRequest getGameLoginRequest(ServerRequest serverRequest) {
        GameLoginRequest gameLoginRequest = new GameLoginRequest();
        gameLoginRequest.setUserName(serverRequest.getParam("u"));
        gameLoginRequest.setPassword(serverRequest.getParam("p"));
        gameLoginRequest.setUserType(serverRequest.getParam("t"));
        gameLoginRequest.setGameId(serverRequest.getParam("g"));
        return gameLoginRequest;
    }

    public GameLoginServerResponse login(ServerRequest serverRequest) {
        GameLoginRequest gameLoginRequest = getGameLoginRequest(serverRequest);
        GameLoginServerResponse gameLoginServerResponse = gameLoginServerProxy.gameLogin(gameLoginRequest);
        return gameLoginServerResponse;
    }

    public GameLoginServerResponse login(GameLoginRequest gameLoginRequest) {
        GameLoginServerResponse gameLoginServerResponse = gameLoginServerProxy.gameLogin(gameLoginRequest);
        return gameLoginServerResponse;
    }
}
