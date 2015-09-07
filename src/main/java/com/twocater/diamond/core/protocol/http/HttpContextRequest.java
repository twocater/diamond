package com.twocater.diamond.core.protocol.http;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.diamond.core.server.AbstractContext;
import com.twocater.diamond.core.context.AbstractContextRequest;
import com.twocater.diamond.kit.mapping.MappingResult;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author cpaladin
 */
abstract class HttpContextRequest extends AbstractContextRequest implements HttpRequest {

    protected HttpServerRequest httpServerRequest;

    public HttpContextRequest(AbstractContext abtractContext, HttpServerRequest httpServerRequest) {
        super(abtractContext);
        this.httpServerRequest = httpServerRequest;
    }

    @Override
    public String getContentType() {
        return httpServerRequest.getHttpRequestMessage().getContentType();
    }

    @Override
    public String getContentEncoding() {
        return httpServerRequest.getHttpRequestMessage().getContentEncoding();
    }

    @Override
    public int getContentLength() {
        return httpServerRequest.getHttpRequestMessage().getContentLength();
    }

    @Override
    public String getFilterPath() {
        String path = getPath();
        // String contextPath = context.getContextPath();
        // if ("/".equals(contextPath)) {
        // this.contextPath = "";
        // } else {
        // this.contextPath = contextPath;
        // path = path.substring(contextPath.length());
        // }
        return path;
    }

    @Override
    public String mappingService() {
        // 如果已经映射过服务，直接返回
        if (mapped) {
            return this.serviceName;
        }
        String path = getFilterPath();
        System.out.println(path);
        MappingResult mappingResult = context.getServiceMapping().mapping(path);
        if (mappingResult == null) {
            return null;
        }
        this.servicePath = mappingResult.getMappingPath();
        this.pathInfo = mappingResult.getExtraPath();
        this.serviceName = mappingResult.getDesName();
        return this.serviceName;
    }

    @Override
    public HttpMethod getMethod() {
        return this.httpServerRequest.getHttpRequestMessage().getMethod();
    }

    @Override
    public HttpVersion getProtocolVersion() {
        return this.httpServerRequest.getHttpRequestMessage().getProtocolVersion();
    }

    @Override
    public String getUri() {
        return this.httpServerRequest.getHttpRequestMessage().getUri();
    }

    @Override
    public String getPath() {
        return httpServerRequest.getHttpRequestMessage().getPath();
    }

    @Override
    public String getQueryString() {
        return this.httpServerRequest.getHttpRequestMessage().getQueryString();
    }

    @Override
    public Map<String, List<String>> getQuerys() {
        return this.httpServerRequest.getHttpRequestMessage().getQuerys();
    }

    @Override
    public List<String> getQuerys(String name) {
        return this.httpServerRequest.getHttpRequestMessage().getQuerys(name);
    }

    @Override
    public String getQuery(String name) {
        return this.httpServerRequest.getHttpRequestMessage().getQuery(name);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.httpServerRequest.getHttpRequestMessage().getHeaders();
    }

    @Override
    public boolean containHeader(String name) {
        return this.httpServerRequest.getHttpRequestMessage().containHeader(name);
    }

    @Override
    public String getHeader(String name) {
        return httpServerRequest.getHttpRequestMessage().getHeader(name);
    }

    @Override
    public Set<String> getHeaderNames() {
        return this.httpServerRequest.getHttpRequestMessage().getHeaderNames();
    }

    @Override
    public String getHost() {
        return this.httpServerRequest.getHttpRequestMessage().getHost();
    }

    @Override
    public Map<String, String> getCookies() {
        return this.httpServerRequest.getHttpRequestMessage().getCookies();
    }

    @Override
    public boolean isKeepAlive() {
        return this.httpServerRequest.getHttpRequestMessage().isKeepAlive();
    }

    @Override
    public List<String> getHeaders(String name) {
        return httpServerRequest.getHttpRequestMessage().getHeaders(name);
    }

}
