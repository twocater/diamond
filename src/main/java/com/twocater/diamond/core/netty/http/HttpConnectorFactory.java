/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.netty.http;

import com.twocater.diamond.core.netty.AbstractNettyConnectorFactory;
import com.twocater.diamond.core.netty.NettyHandlerFactory;

/**
 *
 * @author cpaladin
 */
public class HttpConnectorFactory extends AbstractNettyConnectorFactory {

    @Override
    protected NettyHandlerFactory createHandlerFactory() {
        return new HttpHandlerFactory();
    }

}
