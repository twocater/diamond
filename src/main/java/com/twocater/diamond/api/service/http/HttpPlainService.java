/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.service.http;

import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.api.service.Service;
import com.twocater.diamond.api.service.ServiceConfig;

/**
 * @author cpaladin
 */
public abstract class HttpPlainService implements Service {
    protected ServiceConfig serviceConfig;

    protected abstract void service(HttpPlainRequest request);

    @Override
    public void init(ServiceConfig config) throws Exception {
        this.serviceConfig = config;
        init();
    }

    protected void init() {
    }

    @Override
    public void service(Request request) throws Exception {
        service((HttpPlainRequest) request);

    }

    @Override
    public void destroy() {

    }
}
