/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.server.ServerRequest;

/**
 *
 * @author cpaladin
 */
public class HttpServerRequest implements ServerRequest {

    private final HttpRequestMessage httpRequestMessage;

    public HttpServerRequest(HttpRequestMessage httpRequestMessage) {
        this.httpRequestMessage = httpRequestMessage;
    }

    public HttpRequestMessage getHttpRequestMessage() {
        return httpRequestMessage;
    }

}
