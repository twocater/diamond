package com.bianfeng.dayou.accessserver.server;

/**
 * Created by Administrator on 2015/12/12.
 */
public class ServerFactory {

    private static final ServerFactory instance = new ServerFactory();

    public static ServerFactory getInstance() {
        return instance;
    }

    private GameLoginSer gameLoginSer = new GameLoginSer();


    public GameLoginSer getGameLoginSer() {
        return gameLoginSer;
    }
}
