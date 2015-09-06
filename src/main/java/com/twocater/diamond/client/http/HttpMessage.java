package com.twocater.diamond.client.http;

import com.twocater.diamond.util.HttpUtil;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpMessage implements HttpMessageHeader {
    private Map<String, List<String>> headers;
    private byte[] data;

    private boolean cookieParsed;
    private Map<String, String> cookies = new HashMap<String, String>();
    private boolean contentTypeParsed;
    private String contentType;
    private String contentEncoding;

    public HttpMessage(Map<String, List<String>> headers, byte[] data) {
        this.headers = headers;
        this.data = data;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public boolean containHeader(String name) {
        return headers.containsKey(name);
    }

    @Override
    public List<String> getHeaders(String name) {
        return headers.get(name);
    }

    @Override
    public String getHeader(String name) {
        List<String> list = getHeaders(name);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public Map<String, String> getCookies() {
        parseCookie();
        return cookies;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public boolean isKeepAlive() {
        return HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(getHeader(HttpHeaders.Names.CONNECTION));
    }

    @Override
    public String getContentType() {
        parsedContentType();
        return contentType;
    }

    @Override
    public String getContentEncoding() {
        parsedContentType();
        return contentEncoding;
    }

    @Override
    public String getDate() {
        return getHeader(HttpHeaders.Names.DATE);
    }

    private void parseCookie() {
        if (!cookieParsed) {
            synchronized (this) {
                if (!cookieParsed) {
                    List<String> cookie = getHeaders(HttpHeaders.Names.SET_COOKIE);
                    if (cookie != null) {
                        for (String c : cookie) {
                            HttpUtil.parseSetCookie(cookies, c);
                        }
                    }
                    cookieParsed = true;
                }
            }
        }
    }

    private void parsedContentType() {
        if (!contentTypeParsed) {
            synchronized (this) {
                if (!contentTypeParsed) {
                    contentTypeParsed = true;
                    String contentType = getHeader(HttpHeaders.Names.CONTENT_TYPE);
                    if (contentType == null) {
                        return;
                    }
                    int index = contentType.indexOf(';');
                    if (index == -1) {
                        String encoding = HttpUtil.parseCharset(contentType);
                        if (encoding == null) {
                            this.contentType = contentType.trim();
                        } else {
                            this.contentEncoding = encoding;
                        }
                        return;
                    }
                    this.contentType = contentType.substring(0, index).trim();
                    contentType = contentType.substring(index + 1).trim();
                    this.contentEncoding = HttpUtil.parseCharset(contentType);
                }
            }
        }
    }

}
