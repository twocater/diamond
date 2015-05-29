package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpResponse;
import com.twocater.diamond.api.service.http.HttpJsonResponse;

/**
 *
 * @author cpaladin
 */
class HttpJsonContextResponse extends HttpContextResponse implements HttpJsonResponse {

    public HttpJsonContextResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

}
