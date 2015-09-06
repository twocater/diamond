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

    public AbstractContextRequest(AbstractContext context) {
        this.context = context;
    }
}
