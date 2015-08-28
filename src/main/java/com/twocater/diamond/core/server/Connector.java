package com.twocater.diamond.core.server;

import com.twocater.diamond.core.server.ServerContext;

/**
 *
 * @author cpaladin
 */
public interface Connector {

	int getPort();

	void bind() throws Exception;

	void unbind() throws Exception;

	ServerContext getServerConetxt();

}
