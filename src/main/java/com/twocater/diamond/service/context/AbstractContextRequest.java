package com.twocater.diamond.service.context;

import com.twocater.diamond.server.AbstractContext;
import com.twocater.diamond.server.ContextRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public abstract class AbstractContextRequest implements ContextRequest {

	protected Map<String, Object> attributes = new HashMap<String, Object>();
	protected AbstractContext context;

	public AbstractContextRequest(AbstractContext context) {
		this.context = context;
	}

}
