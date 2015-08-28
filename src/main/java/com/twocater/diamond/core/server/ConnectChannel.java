package com.twocater.diamond.core.server;

import java.net.SocketAddress;

public interface ConnectChannel {
	SocketAddress getLocalAddress();

	SocketAddress getRemoteAddress();

	ServerRequest read() throws Exception;

	void write() throws Exception;

	void error(Exception e) throws Exception;

}
