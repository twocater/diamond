/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

import com.twocater.diamond.core.server.Server;
import com.twocater.diamond.core.server.parse.SpringXmlServerFactory;

import java.lang.management.ManagementFactory;

/**
 * @author cpaladin
 */
public class SpringXmlServerLifeCycle implements LifeCycle {

    private Server server;
    private long startTime;

    @Override
    public void init() throws Exception {
        SpringXmlServerFactory serverFactory = new SpringXmlServerFactory();
        server = serverFactory.createServer();
    }

    @Override
    public void start() throws Exception {
        startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        System.out.println(String.format("start server...[%s]", getClassName()));
        server.startup();
        String string = String.format("start server success, use time:%d ms.[%s]", System.currentTimeMillis() - startTime, getClassName());
        System.out.println(string);
    }

    @Override
    public void stop() throws Exception {
        System.out.println(String.format("stop server...[%s]", getClassName()));
        long start = System.currentTimeMillis();
        server.shutdown();
        long endTime = System.currentTimeMillis();
        String string = String.format("stop server success, use time:%d ms, totally run %d.[%s]", endTime - start, endTime - startTime, getClassName());
        System.out.println(string);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(String.format("destroy server...[%s]", getClassName()));
        System.out.println(String.format("destroy server finish.[%s]", getClassName()));
    }

    private String getClassName() {
        return this.getClass().getName();
    }

}
