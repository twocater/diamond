package com.twocater.diamond.api.service;

public abstract class Service<T extends Request> {

	abstract void service(T request) throws Exception;

}
