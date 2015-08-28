package com.twocater.diamond.core.protocol.http;


import com.twocater.diamond.api.protocol.http.HttpResponse;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cpaladin
 */
public class HttpResponseMessage implements HttpResponse {

    private HttpResponseStatus status;
    private HttpVersion httpVersion;

    private byte[] data;

    private String contentType;
    private String contentEncoding;

    private Map<String, List<String>> headers = new HashMap<String, List<String>>();


    @Override
    public void setStatus(HttpResponseStatus status) {
        this.status = status;
    }

    @Override
    public HttpResponseStatus getStatus() {
        return status;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setContentLength(int length) {
        setHeader(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(length));
    }

    @Override
    public void setHeader(String name, String value) {
        List<String> list = null;
        if (value != null) {
            list = new ArrayList<String>();
            list.add(value);
        }
        setHeaders(name, list);
    }

    @Override
    public void setHeaders(String name, List<String> value) {
        if (value == null) {
            headers.remove(name);
            if (HttpHeaders.Names.CONTENT_TYPE.equals(name)) {
                contentType = null;
                contentEncoding = null;
            }
            return;
        }
        if (name == null || name.isEmpty()) {
            return;
        }
        headers.put(name, value);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public void addHeader(String name, String value) {
        if (value == null) {
            return;
        }
        List<String> list = getHeaders(name);
        if (list == null) {
            setHeader(name, value);
            return;
        }
        list.add(value);
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
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (contentEncoding == null && contentType == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, null);
        } else if (contentEncoding == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, contentType);
        } else if (contentType == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, "charset=" + contentEncoding);
        } else {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, contentType + ";charset=" + contentEncoding);
        }

    }

    @Override
    public void setKeepAlive(boolean keepAlive) {
        setHeader(HttpHeaders.Names.CONNECTION, keepAlive ? HttpHeaders.Values.KEEP_ALIVE : HttpHeaders.Values.CLOSE);

    }

    @Override
    public boolean isKeepAlive() {
        return HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(getHeader(HttpHeaders.Names.CONNECTION));
    }

    @Override
    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
        if (contentEncoding == null && contentType == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, null);
        } else if (contentEncoding == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, contentType);
        } else if (contentType == null) {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, "charset=" + contentEncoding);
        } else {
            setHeader(HttpHeaders.Names.CONTENT_TYPE, contentType + ";charset=" + contentEncoding);
        }
    }

    @Override
    public String getContentEncoding() {
        return contentEncoding;
    }

    @Override
    public HttpVersion getProtocolVersion() {
        return httpVersion;
    }

    @Override
    public void setProtocolVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

}
