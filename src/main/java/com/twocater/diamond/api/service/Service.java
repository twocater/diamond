package com.twocater.diamond.api.service;

public interface Service {

    void init(ServiceConfig config) throws Exception;

    void service(Request request) throws Exception;

    void destroy();
}
