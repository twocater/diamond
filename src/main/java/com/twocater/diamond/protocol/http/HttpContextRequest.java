/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.server.AbstractContext;

/**
 *
 * @author cpaladin
 */
abstract class HttpContextRequest extends AbstractContextRequest{

    protected HttpServerRequest httpServerRequest;

    public HttpContextRequest(AbstractContext abtractContext, HttpServerRequest httpServerRequest) {
        super(abtractContext);
        this.httpServerRequest = httpServerRequest;
    }

}
