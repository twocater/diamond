/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

/**
 * @author cpaladin
 */
public class StartupMain {

    public static void main(String[] args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.boot();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("start up exception.");
            System.exit(1);
        }

    }

}
