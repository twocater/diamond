package com.twocater.diamond.api.service;

import java.util.EventListener;

public interface RequestAttributeListener extends EventListener {
	void attributeAdded(Request request, String name, Object value);

	void attributeRemoved(Request request, String name, Object value);

	void attributeReplaced(Request request, String name, Object value);
}
