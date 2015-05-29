package com.twocater.diamond.protocol.http;

import java.util.List;

import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.diamond.server.AbstractContext;
import com.twocater.diamond.service.context.AbstractContextRequest;
import com.twocater.kit.mapping.MappingResult;

/**
 *
 * @author cpaladin
 */
abstract class HttpContextRequest extends AbstractContextRequest implements HttpRequest {

	protected HttpServerRequest httpServerRequest;

	public HttpContextRequest(AbstractContext abtractContext, HttpServerRequest httpServerRequest) {
		super(abtractContext);
		this.httpServerRequest = httpServerRequest;
	}

	@Override
	public String getContentType() {
		return httpServerRequest.getHttpRequestMessage().getContentType();
	}

	@Override
	public String getFilterPath() {
		String path = getPath();
		// String contextPath = context.getContextPath();
		// if ("/".equals(contextPath)) {
		// this.contextPath = "";
		// } else {
		// this.contextPath = contextPath;
		// path = path.substring(contextPath.length());
		// }
		return path;
	}

	@Override
	public String mappingService() {
		String path = getFilterPath();
		System.out.println(path);
		MappingResult mappingResult = context.getServiceMapping().mapping(path);
		if (mappingResult == null) {
			return null;
		}
		// this.servicePath = mappingResult.getMappingPath();
		// this.pathInfo = mappingResult.getExtraPath();
		return mappingResult.getDesName();
	}

	@Override
	public String getPath() {
		return httpServerRequest.getHttpRequestMessage().getPath();
	}

	@Override
	public String getHeader(String name) {
		return httpServerRequest.getHttpRequestMessage().getHeader(name);
	}

	@Override
	public List<String> getHeaders(String name) {
		return httpServerRequest.getHttpRequestMessage().getHeaders(name);
	}

}
