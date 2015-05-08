/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server;

import com.twocater.diamond.Connector;
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
        this.context  = context;
    }
    
    public void addConnector(Connector connector) {
        this.connectors.add(connector);
    }

    @Override
    public void startup() throws Exception {
        for (Connector connector : connectors) {
            connector.bind();
        }
    }

    @Override
    public void shutdown() throws Exception {
        for (Connector connector : connectors) {
            connector.unbind();
        }
    }

    @Override
    public void handle(ServerRequest request) throws Exception {
       context.handle(request);
    }

}