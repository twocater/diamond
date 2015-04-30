/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond;

/**
 *
 * @author cpaladin
 */
public class ServerLifeCycle implements LifeCycle {

    private Server server;
    private long startTime;

    @Override
    public void init() throws Exception {
        NettyServerConfig nettyServerConfig = new NettyServerConfig();
        server = new NettyServer(nettyServerConfig);
    }

    @Override
    public void start() throws Exception {
        startTime = System.currentTimeMillis();
        System.out.println("start server...");
        server.startup();
        String string = String.format("start server success, use time:%d ms.", System.currentTimeMillis() - startTime);
        System.out.println(string);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop server...");
        long start = System.currentTimeMillis();
        server.shutdown();
        long endTime = System.currentTimeMillis();
        String string = String.format("stop server success, use time:%d ms, totally run %d.", endTime - start, endTime - startTime);
        System.out.println(string);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy server...");
        System.out.println("destroy server finish.");
    }

}
