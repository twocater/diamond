/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpHeader;
import com.twocater.diamond.api.protocol.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class HttpResponseMessage implements HttpResponse {

    private byte[] data;

    private String contentType;
    private String contentEncoding;

    private Map<String, List<String>> headers = new HashMap<String, List<String>>();

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setContentLength(int length) {
        setHeader(HttpHeader.CONTENT_LENGTH.getName(), String.valueOf(length));
    }

    public void setHeader(String name, String value) {
        List<String> list = null;
        if (value != null) {
            list = new ArrayList<String>();
            list.add(value);
        }
        setHeaders(name, list);
    }

    public void setHeaders(String name, List<String> value) {
        if (value == null) {
            headers.remove(name);
            if (HttpHeader.CONTENT_TYPE.getName().equals(name)) {
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

}
