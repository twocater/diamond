/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty;

import com.twocater.diamond.Connector;
import com.twocater.diamond.server.Server;

/**
 *
 * @author cpaladin
 */
public interface ConnectorFactory {

    Connector createConnector(Server server);
}
