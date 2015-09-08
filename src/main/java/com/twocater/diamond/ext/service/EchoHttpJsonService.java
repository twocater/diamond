package com.twocater.diamond.ext.service;

import com.twocater.diamond.api.service.http.HttpJsonRequest;
import com.twocater.diamond.api.service.http.HttpJsonService;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public class EchoHttpJsonService extends HttpJsonService {

    @Override
    protected void service(HttpJsonRequest request) {
        String uri = request.getUri();
        String path = request.getPath();
        String queryString = request.getQueryString();
        String contentType = request.getContentType();
        String contentEncoding = request.getContentEncoding();
        int contentLength = request.getContentLength();
        Map<String, List<String>> headers = request.getHeaders();
        String host = request.getHost();
        HttpMethod method = request.getMethod();
        HttpVersion protocolVersion = request.getProtocolVersion();
        Map<String, String> cookies = request.getCookies();
        Set<String> initParameterNames = this.serviceConfig.getInitParameterNames();
        String serviceName = this.serviceConfig.getServiceName();


        request.getResponse().addMapEntry("uri", uri);
        request.getResponse().addMapEntry("path", path);
        request.getResponse().addMapEntry("queryString", queryString);
        request.getResponse().addMapEntry("contentType", contentType);
        request.getResponse().addMapEntry("contentEncoding", contentEncoding);
        request.getResponse().addMapEntry("contentLength", contentLength);
        request.getResponse().addMapEntry("headers", headers);
        request.getResponse().addMapEntry("host", host);
        request.getResponse().addMapEntry("method", method.toString());
        request.getResponse().addMapEntry("protocolVersion", protocolVersion.toString());
        request.getResponse().addMapEntry("cookies", cookies);
        request.getResponse().addMapEntry("initParameterNames", initParameterNames);
        request.getResponse().addMapEntry("serviceName", serviceName);
        request.getResponse().addMapEntry("reqeust", request.getMapEntry());
    }
}
