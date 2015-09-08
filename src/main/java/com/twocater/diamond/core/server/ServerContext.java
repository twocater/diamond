/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.server;

import org.slf4j.Logger;

import java.util.Set;

/**
 * @author cpaladin
 */
public interface ServerContext {

    public Logger getLog();

    public void handle(ServerRequest serverRequest) throws Exception;

    public void setServer(Server server);

    String getInitParameter(String name);

    Set<String> getInitParameterNames();

}
