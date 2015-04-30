/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author cpaladin
 */
public class NettyConnector implements Connector {

    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bind() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
//        serverBootstrap.handler(handler);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DiscardServerHandler());
            }
        });

        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);

        serverBootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);//???
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
//        serverBootstrap.childOptionption(ChannelOption.SO_TIMEOUT, 2000);
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        serverBootstrap.childOption(ChannelOption.SO_LINGER, -1);
        serverBootstrap.childOption(ChannelOption.SO_SNDBUF, 20480);
        serverBootstrap.childOption(ChannelOption.SO_RCVBUF, 20480);

        channelFuture = serverBootstrap.bind(8080).sync();
    }

    @Override
    public void unbind() throws Exception {
        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
