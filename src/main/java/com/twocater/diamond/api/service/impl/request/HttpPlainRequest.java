package com.twocater.diamond.api.service.impl.request;

import io.netty.handler.codec.http.HttpRequest;

import com.twocater.diamond.api.service.Request;

public abstract class HttpPlainRequest implements IHttpRequest, Request {

	public HttpRequest httpRequest;
}

interface IHttpRequest {

	public String getQuery(String name);

}
