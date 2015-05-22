/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpResponse;
import com.twocater.diamond.api.service.http.HttpPlainRequest;
import com.twocater.diamond.server.AbstractContext;
import com.twocater.diamond.server.ContextRequest;

/**
 *
 * @author cpaladin
 */
public class HttpJsonContextRequest extends HttpContextRequest implements HttpJsonRequest {

    private HttpJsonContextResponse httpJsonContextResponse;

    public HttpJsonContextRequest(AbstractContext context, HttpServerRequest httpServerRequest) {
        super(context, httpServerRequest);
        httpJsonContextResponse = new HttpJsonContextResponse(httpServerRequest.getHttpRequestMessage().getResponse());
    }

    @Override
    public String getFilterPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String mappingService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void response() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HttpResponse getResponse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
