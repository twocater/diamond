/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.protocol.http;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cpaladin
 */
public interface HttpResponse {

    //回复状态
    void setStatus(HttpResponseStatus status);

    HttpResponseStatus getStatus();

    HttpVersion getProtocolVersion();

    void setProtocolVersion(HttpVersion httpVersion);

    // 回复头
    void setHeaders(String name, List<String> value);

    void setHeader(String name, String value);

    Map<String, List<String>> getHeaders();

    List<String> getHeaders(String name);

    boolean containHeader(String name);

    String getHeader(String name);

    Set<String> getHeaderNames();

    void addHeader(String name, String value);


    // 特定头
    String getContentType();

    void setContentType(String contentType);

    void setKeepAlive(boolean keepAlive);

    boolean isKeepAlive();

    void setContentEncoding(String encoding);

    String getContentEncoding();

}
