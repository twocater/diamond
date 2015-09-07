/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.service;

import com.twocater.diamond.core.server.ServerContext;

import java.net.SocketAddress;

/**
 * @author cpaladin
 */
public interface Request {

    SocketAddress getRemoteAddress();

    SocketAddress getLocalAddress();

    ServerContext getContext();

    String getServicePath();

    String getPathInfo();

}
