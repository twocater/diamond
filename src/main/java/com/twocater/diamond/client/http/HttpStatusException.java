package com.twocater.diamond.client.http;

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public class HttpStatusException extends Exception {
    private HttpResponseStatus status;

    public HttpStatusException(HttpResponseStatus status) {
        this.status = status;
    }

    public HttpResponseStatus getStatus() {
        return this.status;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("HttpStatusException:");
        if (this.status != null) {
            sb.append(this.status.toString());
        }

        return sb.toString();
    }
}
