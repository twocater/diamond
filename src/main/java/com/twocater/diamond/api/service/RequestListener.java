package com.twocater.diamond.api.service;

import java.util.EventListener;

public interface RequestListener extends EventListener {
	void requestDestroyed(Request request);

	void requestInitialized(Request request);
}
