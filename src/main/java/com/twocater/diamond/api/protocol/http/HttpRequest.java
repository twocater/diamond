package com.twocater.diamond.api.protocol.http;

import java.util.List;

/**
 *
 * @author cpaladin
 */
public interface HttpRequest {

	String getContentType();

	HttpResponse getResponse();

	// uri解析
	String getPath();
	
	
	// 请求头
//	Map<String, List<String>> getHeaders();

//	boolean containHeader(String name);

	List<String> getHeaders(String name);

	String getHeader(String name);

//	Set<String> getHeaderNames();
}
