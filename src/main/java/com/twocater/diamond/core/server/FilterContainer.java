package com.twocater.diamond.core.server;

import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.FilterChain;
import com.twocater.diamond.api.service.FilterConfig;
import com.twocater.diamond.api.service.Request;
import com.twocater.kit.mapping.MatchMapping;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现映射功能
 *
 * @author cpaladin
 */
public class FilterContainer implements Filter {

    private Filter filter;
    private MatchMapping mapping;
    private Set<String> patternSet;

    public FilterContainer(Filter filter, List<String> patternList, MatchMapping mapping) {
        this.filter = filter;
        if (patternList != null && patternList.size() > 0) {
            this.patternSet = new HashSet<String>(patternList);
        }
        this.mapping = mapping;
    }

    @Override
    public void init(FilterConfig config) throws Exception {
        filter.init(config);
    }

    @Override
    public void doFilter(Request request, FilterChain chain) throws Exception {
        if (patternSet == null) {
            chain.doFilter(request);
            return;
        }
        boolean match = false;
        String path = ((ContextRequest) request).getFilterPath();
        if (patternSet.contains(path)) {
            match = true;
        } else {
            for (String pattern : patternSet) {
                if (mapping.isMatch(path, pattern)) {
                    match = true;
                    break;
                }
            }
        }
        if (match) {
            filter.doFilter(request, chain);
        } else {
            chain.doFilter(request);
        }
    }

    @Override
    public void destroy() {
        filter.destroy();
    }

}
