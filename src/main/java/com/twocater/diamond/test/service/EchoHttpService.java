package com.twocater.diamond.test.service;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import com.twocater.diamond.api.service.ServiceConfig;
import com.twocater.diamond.api.service.http.HttpPlainRequest;
import com.twocater.diamond.api.service.http.HttpPlainService;
import io.netty.handler.codec.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by chenzhiwei on 15-8-24.
 */
public class EchoHttpService extends HttpPlainService {

    @Override
    protected void init() {
    }

    @Override
    protected void service(HttpPlainRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Uri:").append(request.getUri()).append("\n");
        sb.append("Path:").append(request.getPath()).append("\n");
        sb.append("QueryString:").append(request.getQueryString()).append("\n");
        sb.append("ContentType:").append(request.getContentType()).append("\n");
        sb.append("ContentEncoding:").append(request.getContentEncoding()).append("\n");
        sb.append("ContentLength:").append(request.getContentLength()).append("\n");
        sb.append("Headers:").append(request.getHeaders()).append("\n");
        sb.append("Host:").append(request.getHost()).append("\n");
        sb.append("Method:").append(request.getMethod()).append("\n");
        sb.append("ProtocolVersion:").append(request.getProtocolVersion()).append("\n");
        sb.append("cookies:").append(request.getCookies()).append("\n");

        sb.append("InitParameterNames:").append(this.serviceConfig.getInitParameterNames()).append("\n");
        sb.append("ServiceName:").append(this.serviceConfig.getServiceName()).append("\n");
        ;

        try {
            if (request.getData() != null) {
                sb.append("data:").append(new String(request.getData(), request.getContentEncoding())).append("\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        request.getResponse().setData(sb.toString().getBytes(StandardCharsets.UTF_8));
        request.getResponse().setHeader(HttpHeaders.Names.CONTENT_TYPE, HttpContentType.plain.getContentType());
    }
}
