package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpHeader;
import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.util.HttpUtil;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class HttpRequestMessage implements HttpRequest {

	private HttpMethod httpMethod;
	private HttpVersion httpVersion;
	private String uri;
	private boolean uriParsed;
	private String path;
	private String queryString;
	private boolean queryStringParsed;
	private Map<String, List<String>> queryMap;
	private Map<String, List<String>> headers;
	private boolean cookieParsed;
	private Map<String, String> cookies;
	private boolean contentTypeParsed;
	private String contentType;
	private String contentEncoding;

	private byte[] data;
	private HttpResponseMessage httpResponseMessage;

	public HttpRequestMessage(HttpMethod httpMethod, HttpVersion httpVersion, String uri, Map<String, List<String>> headers, byte[] data) {
		this.httpMethod = httpMethod;
		this.httpVersion = httpVersion;
		this.uri = uri;
		this.headers = headers;
		this.data = data;

		this.httpResponseMessage = new HttpResponseMessage();
	}

	public HttpResponseMessage getResponse() {
		return this.httpResponseMessage;
	}

	@Override
	public String getContentType() {
		parsedContentType();
		return contentType;
	}

	@Override
	public String getPath() {
		parseUri();
		return path;
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

	private void parsedContentType() {
		if (!contentTypeParsed) {
			synchronized (this) {
				if (!contentTypeParsed) {
					contentTypeParsed = true;
					String contentType = getHeader(HttpHeader.CONTENT_TYPE.getName());
					if (contentType == null) {
						return;
					}
					int index = contentType.indexOf(';');
					if (index == -1) {
						String encoding = HttpUtil.parseCharset(contentType);
						if (encoding == null) {
							this.contentType = contentType.trim();
						} else {
							this.contentEncoding = encoding;
						}
						return;
					}
					this.contentType = contentType.substring(0, index).trim();
					contentType = contentType.substring(index + 1).trim();
					this.contentEncoding = HttpUtil.parseCharset(contentType);
				}
			}
		}
	}

	private void parseUri() {
		if (!uriParsed) {
			synchronized (this) {
				if (!uriParsed) {
					int index = uri.indexOf("?");
					if (index == -1) {
						path = uri;
					} else {
						path = uri.substring(0, index);
						queryString = uri.substring(index + 1);
					}
					uriParsed = true;
				}
			}
		}
	}
}
