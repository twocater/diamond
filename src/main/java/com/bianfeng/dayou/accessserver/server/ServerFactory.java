package com.bianfeng.dayou.accessserver.server;

import com.bianfeng.dayou.accessserver.ServerResponse;
import com.twocater.diamond.core.server.ServerContext;

/**
 * Created by Administrator on 2015/12/12.
 */
public class ServerFactory {

    private static final ServerFactory instance = new ServerFactory();

    public static ServerFactory getInstance() {
        return instance;
    }

    private GameLoginSer gameLoginSer = new GameLoginSer();

    private GameLogicSer gameLogicSer = new GameLogicSer();


    public GameLoginSer getGameLoginSer() {
        return gameLoginSer;
    }

    public GameLogicSer getGameLogicSer() {
        return gameLogicSer;
    }
}
