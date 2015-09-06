package com.twocater.diamond.api.service;

import com.twocater.diamond.core.server.ServerContext;

import java.util.EventListener;

public interface ContextListener extends EventListener {
    void contextInitialized(ServerContext context);

    void contextDestroyed(ServerContext context);
}
