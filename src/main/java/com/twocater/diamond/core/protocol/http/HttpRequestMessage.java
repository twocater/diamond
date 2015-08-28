package com.twocater.diamond.core.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.util.HttpUtil;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cpaladin
 */
public class HttpRequestMessage implements HttpRequest {

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
        parsedContentType();
        return contentType;
    }

    @Override
    public String getContentEncoding() {
        parsedContentType();
        return contentEncoding;
    }

    @Override
    public int getContentLength() {
        String contentLength = getHeader(HttpHeaders.Names.CONTENT_LENGTH);
        if (HttpUtil.isEmpty(contentLength)) {
            return -1;
        }
        return Integer.parseInt(contentLength.trim());
    }

    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }

    @Override
    public HttpVersion getProtocolVersion() {
        return httpVersion;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getPath() {
        parseUri();
        return path;
    }

    @Override
    public String getQueryString() {
        parseUri();
        return queryString;
    }

    @Override
    public Map<String, List<String>> getQuerys() {
        parseQueryString();
        return queryMap;
    }

    @Override
    public List<String> getQuerys(String name) {
        return getQuerys().get(name);
    }

    @Override
    public String getQuery(String name) {
        List<String> list = getQuerys(name);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(headers);
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
    public String getHost() {
        String host = getHeader(HttpHeaders.Names.HOST);
        if (host == null) {
            return host;
        }
        int index = host.indexOf(":");
        if (index == -1) {
            return host.trim();
        }
        return host.substring(0, index).trim();
    }

    @Override
    public Map<String, String> getCookies() {
        parseCookie();
        return cookies;
    }

    @Override
    public boolean isKeepAlive() {
        return HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(getHeader(HttpHeaders.Names.CONNECTION));
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

    private void parseUri() {
        if (!uriParsed) {
            synchronized (this) {
                if (!uriParsed) {
                    int index = uri.indexOf("?");
                    if (index == -1) {
                        path = uri;
                    } else {
                        path = uri.substring(0, index);
                        queryString = uri.substring(index + 1);
                    }
                    uriParsed = true;
                }
            }
        }
    }

    private void parseCookie() {
        if (!cookieParsed) {
            synchronized (this) {
                if (!cookieParsed) {
                    String cookie = getHeader(HttpHeaders.Names.COOKIE);
                    if (cookie != null) {
                        cookies = HttpUtil.parseCookie(cookie);
                    }
                    cookieParsed = true;
                }
            }
        }
    }

    private void parseQueryString() {
        parseUri();
        if (!queryStringParsed) {
            synchronized (this) {
                if (!queryStringParsed) {
                    queryMap = HttpUtil.parseListParameter(queryString);
                    queryStringParsed = true;
                }
            }
        }
    }

    public byte[] getData() {
        return data;
    }
}
