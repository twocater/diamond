package com.twocater.diamond.core.context;

import com.twocater.diamond.core.server.AbstractContext;
import com.twocater.diamond.core.server.ContextRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cpaladin
 */
public abstract class AbstractContextRequest implements ContextRequest {

    protected Map<String, Object> attributes = new HashMap<String, Object>();
    protected AbstractContext context;
    protected String servicePath;
    protected String pathInfo;
    protected String serviceName;
    protected boolean mapped;

    public AbstractContextRequest(AbstractContext context) {
        this.context = context;
    }


    @Override
    public AbstractContext getContext() {
        return context;
    }


    @Override
    public String getServicePath() {
        return servicePath;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }
}
