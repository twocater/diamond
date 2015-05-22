package com.twocater.diamond.server.parse;

import com.twocater.diamond.netty.NettyHandlerFactory;

public class ConnectorConfig {

    private int port;
    private String protocol;

    private String handlerFactory;

    private int so_backlog_parent;
    private boolean so_reuseaddr_parent;

    private boolean tcp_nodelay;
    private boolean so_keepalive;
    private boolean so_reuseaddr;
    private int so_linger;
    private int so_sndbuf;
    private int so_rcvbuf;

    public String getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(String handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getSo_backlog_parent() {
        return so_backlog_parent;
    }

    public void setSo_backlog_parent(int so_backlog_parent) {
        this.so_backlog_parent = so_backlog_parent;
    }

    public boolean isSo_reuseaddr_parent() {
        return so_reuseaddr_parent;
    }

    public void setSo_reuseaddr_parent(boolean so_reuseaddr_parent) {
        this.so_reuseaddr_parent = so_reuseaddr_parent;
    }

    public boolean isTcp_nodelay() {
        return tcp_nodelay;
    }

    public void setTcp_nodelay(boolean tcp_nodelay) {
        this.tcp_nodelay = tcp_nodelay;
    }

    public boolean isSo_keepalive() {
        return so_keepalive;
    }

    public void setSo_keepalive(boolean so_keepalive) {
        this.so_keepalive = so_keepalive;
    }

    public boolean isSo_reuseaddr() {
        return so_reuseaddr;
    }

    public void setSo_reuseaddr(boolean so_reuseaddr) {
        this.so_reuseaddr = so_reuseaddr;
    }

    public int getSo_linger() {
        return so_linger;
    }

    public void setSo_linger(int so_linger) {
        this.so_linger = so_linger;
    }

    public int getSo_sndbuf() {
        return so_sndbuf;
    }

    public void setSo_sndbuf(int so_sndbuf) {
        this.so_sndbuf = so_sndbuf;
    }

    public int getSo_rcvbuf() {
        return so_rcvbuf;
    }

    public void setSo_rcvbuf(int so_rcvbuf) {
        this.so_rcvbuf = so_rcvbuf;
    }

}
