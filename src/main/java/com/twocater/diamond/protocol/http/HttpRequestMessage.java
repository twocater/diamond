/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class HttpRequestMessage implements HttpRequest{

    private HttpMethod httpMethod;
    private HttpVersion httpVersion;
    private String uri;
    private boolean uriParsed;
    private String path;
    private String queryString;
    private boolean queryStringParsed;
    private Map<String, List<String>> queryMap;
    private Map<String, List<String>> headers;
    private boolean cookieParsed;
    private Map<String, String> cookies;
    private boolean contentTypeParsed;
    private String contentType;
    private String contentEncoding;

    private byte[] data;
    private HttpResponseMessage httpResponseMessage;

    public HttpRequestMessage(HttpMethod httpMethod, HttpVersion httpVersion, String uri, Map<String, List<String>> headers, byte[] data) {
        this.httpMethod = httpMethod;
        this.httpVersion = httpVersion;
        this.uri = uri;
        this.headers = headers;
        this.data = data;

        this.httpResponseMessage = new HttpResponseMessage();
    }

    public HttpResponseMessage getResponse() {
        return this.httpResponseMessage;
    }

    @Override
    public String getContentType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
