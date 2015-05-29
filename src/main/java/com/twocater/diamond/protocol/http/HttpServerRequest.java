package com.twocater.diamond.protocol.http;

import com.twocater.diamond.server.ConnectChannel;
import com.twocater.diamond.server.ServerRequest;

/**
 *
 * @author cpaladin
 */
public class HttpServerRequest implements ServerRequest {

	private final ConnectChannel connectChannel;

	private final HttpRequestMessage httpRequestMessage;

	public HttpServerRequest(ConnectChannel connectChannel, HttpRequestMessage httpRequestMessage) {
		this.connectChannel = connectChannel;
		this.httpRequestMessage = httpRequestMessage;
	}

	public HttpRequestMessage getHttpRequestMessage() {
		return httpRequestMessage;
	}

	@Override
	public ConnectChannel getServerChannel() {
		return connectChannel;
	}

}
