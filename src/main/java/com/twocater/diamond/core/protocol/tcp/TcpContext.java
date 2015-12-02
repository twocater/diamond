package com.twocater.diamond.core.protocol.tcp;

import com.twocater.diamond.core.server.AbstractContext;
import com.twocater.diamond.core.server.ContextRequest;
import com.twocater.diamond.core.server.ServerRequest;
import com.twocater.diamond.kit.mapping.MatchMapping;
import com.twocater.diamond.kit.mapping.SingleMapping;

import java.util.Map;

/**
 * Created by chenzhiwei on 2015/12/2.
 */
public class TcpContext extends AbstractContext {
    @Override
    protected ContextRequest createRequest(ServerRequest serverRequest) {
        return null;
    }

    @Override
    protected MatchMapping createFilterMapping() {
        return null;
    }

    @Override
    protected SingleMapping createServiceMapping(Map<String, String> mappings) {
        return null;
    }
}
