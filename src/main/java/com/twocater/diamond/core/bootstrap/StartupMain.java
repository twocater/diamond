/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

import com.twocater.diamond.core.bootstrap.Bootstrap;

/**
 *
 * @author cpaladin
 */
public class StartupMain {

    //监听器
    //过滤器
    public static void main(String[] args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.boot();

//            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//            serverSocketChannel.socket().bind(new InetSocketAddress("192.168.200.41", 8080));
//            serverSocketChannel.configureBlocking(false);
//
//            Selector selector = Selector.open();
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
//            System.err.println("select...");
//            selector.select();
//            System.err.println("select");
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iterator = selectedKeys.iterator();
//            while (iterator.hasNext()) {
//
//                SelectionKey next = iterator.next();
////                next.channel()
//                System.err.println(next);
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("start up exception.");
            System.exit(1);
        }

    }

}