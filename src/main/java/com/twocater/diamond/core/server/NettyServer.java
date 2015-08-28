package com.twocater.diamond.core.server;

import com.twocater.diamond.core.server.Connector;
import com.twocater.diamond.core.bootstrap.LifeCycle;
import com.twocater.diamond.core.server.parse.ServerConfig;

import java.util.ArrayList;
import java.util.List;

/**
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

    private ServerConfig serverConfig;

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

    @Override
    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    @Override
    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
}
