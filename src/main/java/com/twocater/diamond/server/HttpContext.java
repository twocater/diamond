/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server;

import com.twocater.diamond.protocol.http.HttpJsonContextRequest;
import com.twocater.diamond.protocol.http.HttpMapContextRequest;
import com.twocater.diamond.protocol.http.HttpPlainContextRequest;
import com.twocater.diamond.protocol.http.HttpServerRequest;
import com.xunlei.game.api.protocol.http.HttpContentType;

/**
 *
 * @author cpaladin
 */
public class HttpContext extends AbstractContext {

    @Override
    protected ContextRequest createRequest(ServerRequest serverRequest) {
        HttpServerRequest httpServerRequest = (HttpServerRequest) serverRequest;

        String contentType = httpServerRequest.getHttpRequestMessage().getContentType();
        HttpContentType httpContentType = HttpContentType.getHttpContentType(contentType);
        if (httpContentType == null) {
            httpContentType = HttpContentType.plain;
        }
        ContextRequest contextRequest = null;
        switch (httpContentType) {
            case plain:
                contextRequest = new HttpPlainContextRequest(this, httpServerRequest);
                break;
            case json:
                contextRequest = new HttpJsonContextRequest(this, httpServerRequest);
                break;
            case tmap:
                contextRequest = new HttpMapContextRequest(this, httpServerRequest);
                break;
        }
        return contextRequest;

    }

}
