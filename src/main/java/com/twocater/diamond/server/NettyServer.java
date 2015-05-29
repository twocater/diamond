package com.twocater.diamond.server;

import com.twocater.diamond.Connector;
import com.twocater.diamond.LifeCycle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cpaladin
 */
public class NettyServer implements Server {

	private final List<Connector> connectors = new ArrayList<Connector>();

	private ServerContext context;

	public void setServerContext(ServerContext context) {
		this.context = context;
	}

	public void addConnector(Connector connector) {
		this.connectors.add(connector);
	}

	@Override
	public void startup() throws Exception {
		for (Connector connector : connectors) {
			LifeCycle lifeCycle = (LifeCycle) connector.getServerConetxt();
			lifeCycle.init();
		}

		for (Connector connector : connectors) {
			connector.bind();
		}
	}

	@Override
	public void shutdown() throws Exception {
		for (Connector connector : connectors) {
			connector.unbind();
		}

		for (Connector connector : connectors) {
			LifeCycle lifeCycle = (LifeCycle) connector.getServerConetxt();
			lifeCycle.destroy();
		}
	}

	@Override
	public void handle(ServerRequest request) throws Exception {
		context.handle(request);
	}

}
