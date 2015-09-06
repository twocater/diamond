package com.twocater.diamond.api.filter;

import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.FilterConfig;
import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.core.server.ContextRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-9-2.
 */
public class RequestTimeFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger("com.twocater.diamond.request");

    @Override
    public void init(FilterConfig config) throws Exception {

    }

    @Override
    public void doFilter(Request request, FilterChain chain) throws Exception {
        ContextRequest contextRequest = (ContextRequest) request;
        long s = System.currentTimeMillis();
        chain.doFilter(request);
        log.info("{}->{}->{}", new Object[]{System.currentTimeMillis(), contextRequest.mappingService(), System.currentTimeMillis() - s});
    }

    @Override
    public void destroy() {

    }
}
