/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpResponse;

/**
 *
 * @author cpaladin
 */
class HttpContextResponse implements HttpResponse {

    private HttpResponse httpResponse;

    public HttpContextResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
