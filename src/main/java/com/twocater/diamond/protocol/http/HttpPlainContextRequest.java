/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.service.http.HttpPlainRequest;
import com.twocater.diamond.api.service.http.HttpPlainResponse;
import com.twocater.diamond.server.AbstractContext;

/**
 *
 * @author cpaladin
 */
public class HttpPlainContextRequest extends HttpContextRequest implements HttpPlainRequest {

    private HttpPlainContextResponse httpPlainContextResponse;

    public HttpPlainContextRequest(AbstractContext context, HttpServerRequest httpServerRequest) {
        super(context, httpServerRequest);
        this.httpPlainContextResponse = new HttpPlainContextResponse(httpServerRequest.getHttpRequestMessage().getResponse());
    }

    @Override
    public void response() throws Exception {

        //设置内容、内容长度
        byte[] data = httpPlainContextResponse.getData();
        httpServerRequest.getHttpRequestMessage().getResponse().setData(data);// 内容
        httpServerRequest.getHttpRequestMessage().getResponse().setContentLength(data != null ? data.length : 0);// 内容长度
//        httpServerRequest.get
    }

    @Override
    public HttpPlainResponse getResponse() {
        return this.httpPlainContextResponse;
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
