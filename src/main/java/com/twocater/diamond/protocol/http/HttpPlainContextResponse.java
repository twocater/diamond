/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpResponse;
import com.twocater.diamond.api.service.http.HttpPlainResponse;

/**
 *
 * @author cpaladin
 */
class HttpPlainContextResponse extends HttpContextResponse implements HttpPlainResponse {

    private byte[] data;

    public HttpPlainContextResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public byte[] getData() {
        return this.data;
    }
}
