package com.twocater.diamond.ext.filter;

import com.twocater.diamond.api.annotation.AFilter;
import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.FilterConfig;
import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.util.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by chenzhiwei on 15-9-9.
 */

@Component
@AFilter(paths = "/*")
public class RequestLogFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoggerConstant.REQUEST);

    @Override
    public void init(FilterConfig config) throws Exception {

    }

    @Override
    public void doFilter(Request request, FilterChain chain) throws Exception {
        if (request instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) request;
            log.info("{}->{}", new Object[]{httpRequest.getUri(), request.getRemoteAddress()});
        }
        chain.doFilter(request);
    }

    @Override
    public void destroy() {

    }
}
