package com.twocater.diamond.client.http;

import com.twocater.diamond.api.service.json.JsonEntryData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpJsonMessage extends JsonEntryData implements HttpMessageHeader {
    private HttpMessage httpMessage;

    public HttpJsonMessage(HttpMessage httpMessage, Object entryData) {
        super(entryData);
        this.httpMessage = httpMessage;
    }

    public Map<String, List<String>> getHeaders() {
        return httpMessage.getHeaders();
    }

    @Override
    public boolean containHeader(String name) {
        return httpMessage.containHeader(name);
    }

    @Override
    public List<String> getHeaders(String name) {
        return httpMessage.getHeaders(name);
    }

    @Override
    public String getHeader(String name) {
        return httpMessage.getHeader(name);
    }

    @Override
    public Set<String> getHeaderNames() {
        return httpMessage.getHeaderNames();
    }

    @Override
    public Map<String, String> getCookies() {
        return httpMessage.getCookies();
    }

    @Override
    public boolean isKeepAlive() {
        return httpMessage.isKeepAlive();
    }

    @Override
    public String getContentType() {
        return httpMessage.getContentType();
    }

    @Override
    public String getContentEncoding() {
        return httpMessage.getContentEncoding();
    }

    @Override
    public String getDate() {
        return httpMessage.getDate();
    }
}
