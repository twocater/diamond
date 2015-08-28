package com.twocater.diamond.api.service.http;

import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.api.service.Service;
import com.twocater.diamond.api.service.ServiceConfig;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public abstract class HttpJsonService implements Service {
    protected ServiceConfig serviceConfig;

    protected abstract void service(HttpJsonRequest request);

    @Override
    public void init(ServiceConfig config) throws Exception {
        this.serviceConfig = config;
        init();
    }

    protected void init() {
    }

    @Override
    public void service(Request request) throws Exception {
        service((HttpJsonRequest) request);

    }

    @Override
    public void destroy() {

    }
}
