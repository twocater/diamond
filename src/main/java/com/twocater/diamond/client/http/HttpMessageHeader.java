package com.twocater.diamond.client.http;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HttpMessageHeader {
    Map<String, List<String>> getHeaders();

    boolean containHeader(String name);

    List<String> getHeaders(String name);

    String getHeader(String name);

    Set<String> getHeaderNames();

    Map<String, String> getCookies();

    boolean isKeepAlive();

    String getContentType();

    String getContentEncoding();

    String getDate();

}
