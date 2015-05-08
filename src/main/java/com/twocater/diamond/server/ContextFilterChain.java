/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server;

import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.Request;
import com.twocater.diamond.api.service.Service;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class ContextFilterChain implements FilterChain {

    private Map<String, ServiceContainer> services;
    private List<FilterContainer> filters;
    private int pos = 0;
    private int n = 0;

    public ContextFilterChain(Map<String, ServiceContainer> services, List<FilterContainer> filters) {
        this.services = services;
        if (filters != null && filters.size() > 0) {
            this.filters = filters;
            n = this.filters.size();
        }
    }

    @Override
    public void doFilter(Request request) throws Exception {
        if (pos < n) {
            Filter filter = filters.get(pos);
            pos++;
            filter.doFilter(request, this);
            return;
        }
        ContextRequest contextRequest = (ContextRequest) request;
        String serviceName = contextRequest.mappingService();
        Service service = services.get(serviceName);
        if (service == null) {
            throw new NotExistException();
        }
        service.service(request);
    }

}
