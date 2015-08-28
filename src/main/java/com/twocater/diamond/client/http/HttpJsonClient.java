package com.twocater.diamond.client.http;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import com.twocater.diamond.api.service.json.JsonSupport;

import java.io.IOException;
import java.util.Map;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public class HttpJsonClient extends HttpClient {

    public HttpJsonClient(String host, int port, int timeout, String contentEncoding) {
        super(host, port, timeout, HttpContentType.json.getContentType(), contentEncoding);
    }

    public HttpJsonMessage requestByPost(String command, Map<String, Object> request) throws Exception {
        try {
            byte[] requestData = null;
            if (request != null) {
                requestData = JsonSupport.encode(request, getContentEncoding());
            }
            HttpMessage httpMessage = requestByPost(command, requestData);
            HttpJsonMessage httpJsonMessage = createHttpJsonMessage(httpMessage);
            return httpJsonMessage;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private HttpJsonMessage createHttpJsonMessage(HttpMessage httpMessage) throws Exception {
        byte[] data = httpMessage.getData();
        Object entryData = null;
        if (data != null && data.length > 0) {
            String encode = httpMessage.getContentEncoding();
            if (encode == null) {
                encode = getContentEncoding();
            }
            entryData = JsonSupport.parse(data, encode);
        }
        return new HttpJsonMessage(httpMessage, entryData);
    }
}
