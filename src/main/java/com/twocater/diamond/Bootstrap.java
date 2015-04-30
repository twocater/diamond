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
public class Bootstrap {

    public void boot() throws Exception {
        System.out.println("==========boot server==========");
        long start = System.currentTimeMillis();
        final ServerLifeCycle lifeCycle = new ServerLifeCycle();
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
        String string = String.format("==========boot server success, use time:%d ms==========", System.currentTimeMillis() - start);
        System.out.println(string);

    }

}
