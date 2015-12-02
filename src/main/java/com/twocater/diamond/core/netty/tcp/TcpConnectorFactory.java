/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty.tcp;

import com.twocater.diamond.core.netty.AbstractNettyConnectorFactory;
import com.twocater.diamond.core.netty.NettyHandlerFactory;
import com.twocater.diamond.core.netty.http.HttpHandlerFactory;

/**
 * @author cpaladin
 */
public class TcpConnectorFactory extends AbstractNettyConnectorFactory {

    @Override
    protected NettyHandlerFactory createHandlerFactory() {
        throw new UnsupportedOperationException("TcpConnectorFactory.createHandlerFactory()");
    }

}
