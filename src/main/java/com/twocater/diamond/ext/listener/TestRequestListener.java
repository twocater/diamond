package com.twocater.diamond.ext.listener;

import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.api.service.RequestListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-9-7.
 */
public class TestRequestListener implements RequestListener {
    private static final Logger log = LoggerFactory.getLogger(TestRequestListener.class);

    @Override
    public void requestDestroyed(Request request) {
//        log.info("request initialized");
    }

    @Override
    public void requestInitialized(Request request) {
//        log.info("request destroyed");
    }
}
