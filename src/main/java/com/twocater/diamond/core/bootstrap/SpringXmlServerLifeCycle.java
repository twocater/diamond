/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

import com.twocater.diamond.core.server.Server;
import com.twocater.diamond.core.server.parse.SpringXmlServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * @author cpaladin
 */
public class SpringXmlServerLifeCycle implements LifeCycle {
    private Logger stdoutLog = LoggerFactory.getLogger(SpringXmlServerLifeCycle.class);
    private Server server;
    private long startTime;

    @Override
    public void init() throws Exception {
        SpringXmlServerFactory serverFactory = new SpringXmlServerFactory();
        server = serverFactory.createServer();
    }

    @Override
    public void start() throws Exception {
        startTime = System.currentTimeMillis();
        stdoutLog.info("server startup...");

        server.startup();
        stdoutLog.info("server startup ok.[{} MS]", new Object[]{System.currentTimeMillis() - startTime});
    }

    @Override
    public void stop() throws Exception {
        stdoutLog.info("stop server...");
        long start = System.currentTimeMillis();
        server.shutdown();
        long endTime = System.currentTimeMillis();
        stdoutLog.info("stop server success, [{} MS], totally run {} MS.", new Object[]{endTime - start, endTime - startTime});
    }

    @Override
    public void destroy() throws Exception {
        stdoutLog.info("destroy server...");
        stdoutLog.info("destroy server finish.");
    }
}
