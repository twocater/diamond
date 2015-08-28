package com.twocater.diamond.core.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import com.twocater.diamond.api.service.http.HttpPlainRequest;
import com.twocater.diamond.api.service.http.HttpPlainResponse;
import com.twocater.diamond.core.server.AbstractContext;

/**
 * @author cpaladin
 */
public class HttpPlainContextRequest extends HttpContextRequest implements HttpPlainRequest {

    private HttpPlainContextResponse httpPlainContextResponse;

    public HttpPlainContextRequest(AbstractContext context, HttpServerRequest httpServerRequest) {
        super(context, httpServerRequest);
        this.httpPlainContextResponse = new HttpPlainContextResponse(httpServerRequest.getHttpRequestMessage().getResponse());
    }

    @Override
    public void response() throws Exception {
        // 设置内容类型
        if (httpPlainContextResponse.getContentType() == null) {
            httpPlainContextResponse.setContentType(HttpContentType.plain.getContentType());// 类型
        }

        // 设置内容、内容长度
        byte[] data = httpPlainContextResponse.getData();
        httpServerRequest.getHttpRequestMessage().getResponse().setData(data);// 内容
        httpServerRequest.getHttpRequestMessage().getResponse().setContentLength(data != null ? data.length : 0);// 内容长度
        // 设置时间、设置头部

        // 写数据
        httpServerRequest.getServerChannel().write();
    }

    @Override
    public HttpPlainResponse getResponse() {
        return this.httpPlainContextResponse;
    }

    @Override
    public byte[] getData() {
        return this.httpServerRequest.getHttpRequestMessage().getData();
    }
}
