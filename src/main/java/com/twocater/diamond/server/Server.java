/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server;

/**
 *
 * @author cpaladin
 */
public interface Server {

    void startup() throws Exception;

    void shutdown() throws Exception;
    
    void handle(ServerRequest request) throws Exception;

}
