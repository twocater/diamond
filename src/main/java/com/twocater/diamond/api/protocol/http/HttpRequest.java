package com.twocater.diamond.api.protocol.http;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cpaladin
 */
public interface HttpRequest {
    // 请求行参数
    HttpMethod getMethod();

    HttpVersion getProtocolVersion();

    String getUri();

    // uri解析
    String getPath();

    String getQueryString();

    Map<String, List<String>> getQuerys();

    List<String> getQuerys(String name);

    String getQuery(String name);

    // 请求头
    Map<String, List<String>> getHeaders();

    boolean containHeader(String name);

    List<String> getHeaders(String name);

    String getHeader(String name);

    Set<String> getHeaderNames();

    // 特定请求头
    String getHost();

    Map<String, String> getCookies();

    boolean isKeepAlive();

    String getContentType();

    String getContentEncoding();

    int getContentLength();

    // 回复
    HttpResponse getResponse();
}
