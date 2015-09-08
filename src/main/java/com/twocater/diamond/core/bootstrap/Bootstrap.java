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

        // http://www.kammerl.de/ascii/AsciiSignature.php
        String asciiJacky =
                " ______      _____     ____       __    __       ____        __      _   ______   \n" +
                        "(_  __ \\    (_   _)   (    )      \\ \\  / /      / __ \\      /  \\    / ) (_  __ \\  \n" +
                        "  ) ) \\ \\     | |     / /\\ \\      () \\/ ()     / /  \\ \\    / /\\ \\  / /    ) ) \\ \\ \n" +
                        " ( (   ) )    | |    ( (__) )     / _  _ \\    ( ()  () )   ) ) ) ) ) )   ( (   ) )\n" +
                        "  ) )  ) )    | |     )    (     / / \\/ \\ \\   ( ()  () )  ( ( ( ( ( (     ) )  ) )\n" +
                        " / /__/ /    _| |__  /  /\\  \\   /_/      \\_\\   \\ \\__/ /   / /  \\ \\/ /    / /__/ / \n" +
                        "(______/    /_____( /__(  )__\\ (/          \\)   \\____/   (_/    \\__/    (______/  ";


        System.err.println(asciiJacky + String.format("服务器启动完毕.[%d MS]", System.currentTimeMillis() - start));
    }

}
