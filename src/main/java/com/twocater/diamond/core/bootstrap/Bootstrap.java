/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * @author cpaladin
 */
public class Bootstrap {
    private Logger stdoutLog = LoggerFactory.getLogger(Bootstrap.class);

    public void boot() throws Exception {
        stdoutLog.info("boot server...");
        long start = ManagementFactory.getRuntimeMXBean().getStartTime();
        final SpringXmlServerLifeCycle lifeCycle = new SpringXmlServerLifeCycle();
        lifeCycle.init();
        lifeCycle.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    lifeCycle.stop();
                    lifeCycle.destroy();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {

                }
            }
        });

        stdoutLog.info("netty: DiamondServer Start OK. [{} MS]", new Object[]{System.currentTimeMillis() - start});

    }

}
