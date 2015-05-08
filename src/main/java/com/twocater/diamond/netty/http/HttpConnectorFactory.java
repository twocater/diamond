/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.netty.http;

import com.twocater.diamond.netty.AbstractNettyConnectorFactory;
import com.twocater.diamond.netty.CoderFactory;
import com.twocater.diamond.netty.NettyHandlerFactory;
import com.twocater.diamond.server.Server;

/**
 *
 * @author cpaladin
 */
public class HttpConnectorFactory extends AbstractNettyConnectorFactory {

    @Override
    protected NettyHandlerFactory createHandlerFactory(Server server, CoderFactory coderFactory) {
        return new HttpHandlerFactory(server, coderFactory);
    }

}
