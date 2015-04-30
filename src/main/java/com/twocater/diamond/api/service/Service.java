package com.twocater.diamond.api.service;

public interface Service<T extends Request> {

	void service(T request) throws Exception;

}
