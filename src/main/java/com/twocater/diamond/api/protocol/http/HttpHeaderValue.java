package com.twocater.diamond.api.protocol.http;

public enum HttpHeaderValue {
	KEEP_ALIVE("keep-alive"), CONN_CLOSE("close");

	private String value;

	HttpHeaderValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
