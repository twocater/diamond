package com.twocater.diamond.api.service.impl.test;

import io.netty.handler.codec.http.HttpRequest;

import com.twocater.diamond.api.service.impl.HttpPlainService;
import com.twocater.diamond.api.service.impl.request.HttpPlainRequest;

public class EchoHttpPlainService extends HttpPlainService {

	@Override
	public void service(HttpPlainRequest request) throws Exception {
		request.getQuery("");
		HttpRequest httpRequest = request.httpRequest;
	}

}
