/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.Connector;
import com.twocater.diamond.server.Server;
import com.twocater.diamond.spring.ConnectorConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author cpaladin
 */
public class NettyConnector implements Connector {

    private final ConnectorConfig connectorConfig;
    private final NettyHandlerFactory nettyHandlerFactory;

    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    private final Server server;

    public NettyConnector(ConnectorConfig connectorConfig, NettyHandlerFactory nettyHandlerFactory, Server server) {
        this.connectorConfig = connectorConfig;
        this.nettyHandlerFactory = nettyHandlerFactory;
        this.server = server;
    }

    @Override
    public int getPort() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bind() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        // serverBootstrap.handler(handler);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(this.nettyHandlerFactory.createChildHandler());

        serverBootstrap.option(ChannelOption.SO_BACKLOG, connectorConfig.getSo_backlog_parent());
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, connectorConfig.isSo_reuseaddr_parent());

        // serverBootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);//???
        // serverBootstrap.childOptionption(ChannelOption.SO_TIMEOUT, 2000);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, connectorConfig.isTcp_nodelay());
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, connectorConfig.isSo_keepalive());

        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, connectorConfig.isSo_reuseaddr());
        serverBootstrap.childOption(ChannelOption.SO_LINGER, connectorConfig.getSo_linger());
        serverBootstrap.childOption(ChannelOption.SO_SNDBUF, connectorConfig.getSo_sndbuf());
        serverBootstrap.childOption(ChannelOption.SO_RCVBUF, connectorConfig.getSo_rcvbuf());

        channelFuture = serverBootstrap.bind(connectorConfig.getPort()).sync();
    }

    @Override
    public void unbind() throws Exception {
        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

}
