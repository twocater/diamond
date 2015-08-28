/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty;

import com.twocater.diamond.core.server.Connector;
import com.twocater.diamond.core.server.Server;
import com.twocater.diamond.core.server.ServerContext;
import com.twocater.diamond.core.server.parse.ConnectorConfig;
import com.twocater.diamond.core.server.parse.ProtocolSupport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty connector<br>
 * 启动端口在此类中绑定
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

    private final ServerContext serverContext;

    public NettyConnector(ConnectorConfig connectorConfig, NettyHandlerFactory nettyHandlerFactory, Server server) throws Exception {
        this.connectorConfig = connectorConfig;
        this.nettyHandlerFactory = nettyHandlerFactory;

        ProtocolSupport protocol = ProtocolSupport.valueOf(connectorConfig.getProtocol());
        this.serverContext = (ServerContext) Class.forName(protocol.getServerContext()).newInstance();
        this.serverContext.setServer(server);

        ((AbstractHandlerFactory) nettyHandlerFactory).setServerContext(serverContext);

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
        ChannelHandlerAdapter serverHandler = this.nettyHandlerFactory.createServerHandler();
        if (serverHandler != null) {
            serverBootstrap.handler(serverHandler);
        }
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

    @Override
    public ServerContext getServerConetxt() {
        return serverContext;
    }

}
