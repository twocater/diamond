package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpHeader;
import com.twocater.diamond.api.protocol.http.HttpHeaderValue;
import com.twocater.diamond.api.protocol.http.HttpResponse;

import io.netty.handler.codec.http.HttpVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cpaladin
 */
public class HttpResponseMessage implements HttpResponse {

	// private HttpStatus status;
	private HttpVersion httpVersion;

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

	@Override
	public void setHeader(String name, String value) {
		List<String> list = null;
		if (value != null) {
			list = new ArrayList<String>();
			list.add(value);
		}
		setHeaders(name, list);
	}

	@Override
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

	@Override
	public Map<String, List<String>> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	@Override
	public void addHeader(String name, String value) {
		if (value == null) {
			return;
		}
		List<String> list = getHeaders(name);
		if (list == null) {
			setHeader(name, value);
			return;
		}
		list.add(value);
	}

	@Override
	public boolean containHeader(String name) {
		return headers.containsKey(name);
	}

	@Override
	public List<String> getHeaders(String name) {
		return headers.get(name);
	}

	@Override
	public String getHeader(String name) {
		List<String> list = getHeaders(name);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
		if (contentEncoding == null && contentType == null) {
			setHeader(HttpHeader.CONTENT_TYPE.getName(), null);
		} else if (contentEncoding == null) {
			setHeader(HttpHeader.CONTENT_TYPE.getName(), contentType);
		} else if (contentType == null) {
			setHeader(HttpHeader.CONTENT_TYPE.getName(), "charset=" + contentEncoding);
		} else {
			setHeader(HttpHeader.CONTENT_TYPE.getName(), contentType + ";charset=" + contentEncoding);
		}

	}

	@Override
	public void setKeepAlive(boolean keepAlive) {
		setHeader(HttpHeader.CONNECTION.getName(), keepAlive ? HttpHeaderValue.KEEP_ALIVE.getValue() : HttpHeaderValue.CONN_CLOSE.getValue());

	}

	@Override
	public boolean isKeepAlive() {
		return HttpHeaderValue.KEEP_ALIVE.getValue().equalsIgnoreCase(getHeader(HttpHeader.CONNECTION.getName()));
	}

	@Override
	public HttpVersion getProtocolVersion() {
		return httpVersion;
	}

	@Override
	public void setProtocolVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

}
