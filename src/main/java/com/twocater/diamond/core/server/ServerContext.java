/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.server;

/**
 *
 * @author cpaladin
 */
public interface ServerContext {

    public void handle(ServerRequest request) throws Exception;
    
    public void setServer(Server server);

}
