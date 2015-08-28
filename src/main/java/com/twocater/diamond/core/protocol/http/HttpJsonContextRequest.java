/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import com.twocater.diamond.api.protocol.http.HttpResponse;
import com.twocater.diamond.api.service.http.HttpJsonRequest;
import com.twocater.diamond.api.service.http.HttpJsonResponse;
import com.twocater.diamond.api.service.json.JsonEntryData;
import com.twocater.diamond.api.service.json.JsonSupport;
import com.twocater.diamond.core.server.AbstractContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author cpaladin
 */
public class HttpJsonContextRequest extends HttpContextRequest implements HttpJsonRequest {

    private HttpJsonContextResponse httpJsonContextResponse;
    private JsonEntryData dataEntry;
    private boolean dataParsed;

    public HttpJsonContextRequest(AbstractContext context, HttpServerRequest httpServerRequest) {
        super(context, httpServerRequest);
        httpJsonContextResponse = new HttpJsonContextResponse(httpServerRequest.getHttpRequestMessage().getResponse());
    }


    @Override
    public void response() throws Exception {
        // 设置数据编码
        if (httpJsonContextResponse.getContentEncoding() == null) {
            httpJsonContextResponse.setContentEncoding(JsonSupport.DATA_ENCODING);
        }

        // 设置内容类型，这里为json
        httpJsonContextResponse.setContentType(HttpContentType.json.getContentType());// 类型

        byte[] data = httpJsonContextResponse.getData();
        Object object = httpJsonContextResponse.getJsonEntryData().getEntryData();
        // 如果业务中没有设置字节内容，但设置了json对象，则将json对象编码成字符串
        if (data == null && object != null) {
            data = JsonSupport.encode(object, httpJsonContextResponse.getContentEncoding());
        }

        // 设置响应内容
        httpServerRequest.getHttpRequestMessage().getResponse().setData(data);
        // 设置响应内容长度
        httpServerRequest.getHttpRequestMessage().getResponse().setContentLength(data != null ? data.length : 0);

        // 写数据
        httpServerRequest.getServerChannel().write();
    }

    @Override
    public HttpJsonResponse getResponse() {
        return httpJsonContextResponse;
    }

    /**
     * 解析Json数据
     */
    private void parseData() {
        // 这里需要同步吗？！
        if (!dataParsed) {
            synchronized (this) {
                if (!dataParsed) {
                    dataParsed = true;
                    byte[] data = httpServerRequest.getHttpRequestMessage().getData();
                    if (data == null || data.length == 0) {
                        dataEntry = new JsonEntryData(null);
                        return;
                    }
                    try {
                        dataEntry = new JsonEntryData(JsonSupport.parse(data, getContentEncoding()));
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                    dataParsed = true;
                }
            }
        }
    }

    @Override
    public List<Object> getListEntry() {
        parseData();
        List<Object> list = dataEntry.getListEntry();
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean containListEntry(Object item) {
        parseData();
        return dataEntry.containListEntry(item);
    }

    @Override
    public boolean containMapEntry(String name) {
        parseData();
        return dataEntry.containMapEntry(name);
    }

    @Override
    public Map<String, Object> getMapEntry() {
        parseData();
        Map<String, Object> map = dataEntry.getMapEntry();
        if (map == null) {
            return null;
        }
        return Collections.unmodifiableMap(map);
    }
}
