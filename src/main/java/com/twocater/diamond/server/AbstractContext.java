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
public abstract class AbstractContext implements ServerContext {

    @Override
    public void handle(ServerRequest request) {
        ContextRequest createRequest = createRequest(request);

    }

    protected abstract ContextRequest createRequest(ServerRequest serverRequest);

}
