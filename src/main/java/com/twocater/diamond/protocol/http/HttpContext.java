package com.twocater.diamond.protocol.http;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import com.twocater.diamond.server.AbstractContext;
import com.twocater.diamond.server.ContextRequest;
import com.twocater.diamond.server.ServerRequest;
import com.twocater.kit.mapping.MatchMapping;
import com.twocater.kit.mapping.SingleMapping;
import com.twocater.kit.mapping.UriMapping;
import com.twocater.kit.mapping.UriMatchMapping;

import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class HttpContext extends AbstractContext {

	@Override
	protected ContextRequest createRequest(ServerRequest serverRequest) {
		HttpServerRequest httpServerRequest = (HttpServerRequest) serverRequest;

		String contentType = httpServerRequest.getHttpRequestMessage().getContentType();
		HttpContentType httpContentType = HttpContentType.getHttpContentType(contentType);
		if (httpContentType == null) {
			httpContentType = HttpContentType.plain;
		}
		ContextRequest contextRequest = null;
		switch (httpContentType) {
		case plain:
			contextRequest = new HttpPlainContextRequest(this, httpServerRequest);
			break;
		case json:
			// contextRequest = new HttpJsonContextRequest(this, httpServerRequest);
			break;
		case tmap:
			// contextRequest = new HttpMapContextRequest(this, httpServerRequest);
			break;
		}
		return contextRequest;

	}

	@Override
	protected MatchMapping createFilterMapping() {
		return new UriMatchMapping();
	}

	@Override
	protected SingleMapping createServiceMapping(Map<String, String> mappings) {
		return new UriMapping(mappings);
	}
}
