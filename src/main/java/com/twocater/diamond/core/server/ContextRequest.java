package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.Request;

/**
 *
 * @author cpaladin
 */
public interface ContextRequest extends Request {

    String getFilterPath();

    String mappingService();

    void response() throws Exception;
}
