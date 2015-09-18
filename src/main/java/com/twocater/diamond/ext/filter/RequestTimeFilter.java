package com.twocater.diamond.ext.filter;

import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.FilterConfig;
import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.core.server.ContextRequest;
import com.twocater.diamond.util.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-9-2.
 */
public class RequestTimeFilter implements Filter {
    private int slowTime;

    @Override
    public void init(FilterConfig config) throws Exception {
        String sSlowTime = config.getInitParameter("slowTime");
        if (sSlowTime != null && !sSlowTime.trim().isEmpty()) {
            slowTime = Integer.parseInt(sSlowTime.trim());
        }
    }

    @Override
    public void doFilter(Request request, FilterChain chain) throws Exception {
        ContextRequest contextRequest = (ContextRequest) request;
        long s = System.currentTimeMillis();
        chain.doFilter(request);
        long t = System.currentTimeMillis() - s;
        LoggerConstant.filterLog.info("RequestTimeFilter->{}->{}->{}", new Object[]{System.currentTimeMillis(), contextRequest.mappingService(), t});
        if (slowTime > 0 && t >= slowTime) {
            LoggerConstant.filterLog.warn("slowRequest->{}->{}->{}", new Object[]{System.currentTimeMillis(), contextRequest.mappingService(), t});
        }
    }

    @Override
    public void destroy() {

    }
}
