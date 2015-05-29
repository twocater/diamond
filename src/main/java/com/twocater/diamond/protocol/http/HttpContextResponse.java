/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.twocater.diamond.api.protocol.http.HttpResponse;

/**
 *
 * @author cpaladin
 */
class HttpContextResponse implements HttpResponse {

	/**
	 * HttpServerRequest.httpRequestMessage.httpResponseMessage
	 */
	private HttpResponse httpResponse;

	public HttpContextResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	@Override
	public String getContentType() {
		return httpResponse.getContentType();
	}

	@Override
	public void setContentType(String contentType) {
		httpResponse.setContentType(contentType);
	}

	@Override
	public void setKeepAlive(boolean keepAlive) {
		httpResponse.setKeepAlive(keepAlive);

	}

	@Override
	public boolean isKeepAlive() {
		return httpResponse.isKeepAlive();
	}

	@Override
	public void setHeaders(String name, List<String> value) {
		httpResponse.setHeaders(name, value);

	}

	@Override
	public void setHeader(String name, String value) {
		httpResponse.setHeader(name, value);

	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return httpResponse.getHeaders();
	}

	@Override
	public List<String> getHeaders(String name) {
		return httpResponse.getHeaders(name);
	}

	@Override
	public boolean containHeader(String name) {
		return httpResponse.containHeader(name);
	}

	@Override
	public String getHeader(String name) {
		return httpResponse.getHeader(name);
	}

	@Override
	public Set<String> getHeaderNames() {
		return httpResponse.getHeaderNames();
	}

	@Override
	public void addHeader(String name, String value) {
		httpResponse.addHeader(name, value);

	}

	@Override
	public HttpVersion getProtocolVersion() {
		return httpResponse.getProtocolVersion();
	}

	@Override
	public void setProtocolVersion(HttpVersion httpVersion) {
		httpResponse.setProtocolVersion(httpVersion);

	}
}
