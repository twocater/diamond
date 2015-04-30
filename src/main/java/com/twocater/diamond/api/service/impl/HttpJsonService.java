package com.twocater.diamond.api.service.impl;

import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.api.service.Service;

public class HttpJsonService implements Service {

	public abstract void service(HttpJsonRequest request) throws Exception;

}
