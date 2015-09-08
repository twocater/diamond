package com.twocater.diamond.ext.filter;

import com.twocater.diamond.api.annotation.AFilter;
import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.FilterConfig;
import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.core.server.ContextRequest;
import com.twocater.diamond.util.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by chenzhiwei on 15-9-6.
 */
@Component
@AFilter(paths = "/", order = 1)
public class PermissonFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoggerConstant.FILTER);

    @Override
    public void init(FilterConfig config) throws Exception {

    }

    @Override
    public void doFilter(Request request, FilterChain chain) throws Exception {
        log.info("PermissonFilter->{}->{}", new Object[]{request.getRemoteAddress(), ((ContextRequest) request).getFilterPath()});
        chain.doFilter(request);
    }

    @Override
    public void destroy() {

    }
}
