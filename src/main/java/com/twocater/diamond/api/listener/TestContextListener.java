package com.twocater.diamond.api.listener;

import com.twocater.diamond.api.service.ContextListener;
import com.twocater.diamond.core.server.ServerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-9-7.
 */
public class TestContextListener implements ContextListener {
    private static final Logger log = LoggerFactory.getLogger(TestContextListener.class);

    @Override
    public void contextInitialized(ServerContext context) {
        log.info("context initialized");
    }

    @Override
    public void contextDestroyed(ServerContext context) {
        log.info("context destroyed");
    }
}
