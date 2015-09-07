package com.twocater.diamond.api.service;

import com.twocater.diamond.core.server.ServerContext;

import java.util.EventListener;

public interface ContextAttributeListener extends EventListener {
    void attributeAdded(ServerContext context, String name, Object value);

    void attributeRemoved(ServerContext context, String name, Object value);

    void attributeReplaced(ServerContext context, String name, Object value);
}
