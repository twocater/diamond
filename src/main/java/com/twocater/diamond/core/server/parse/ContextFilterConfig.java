package com.twocater.diamond.core.server.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzhiwei on 15-9-2.
 */
public class ContextFilterConfig implements Comparable<ContextFilterConfig> {

    private String filterName;
    private String filterClass;
    private Map<String, String> params = new HashMap<String, String>();
    private List<String> filterPaths = new ArrayList<String>();
    private int order;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public Map<String, String> getParams() {
        return params;
    }

//    public void addParam(String name, String value) {
//        params.put(name, value);
//    }

    public List<String> getFilterPaths() {
        return filterPaths;
    }

//    public void addFilterPath(String filterPath) {
//        filterPaths.add(filterPath);
//    }


    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setFilterPaths(List<String> filterPaths) {
        this.filterPaths = filterPaths;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(ContextFilterConfig o) {
        if (order == 0) {
            return o.getOrder() > 0 ? 1 : 0;
        }
        if (o.getOrder() == 0) {
            return -1;
        }
        return o.getOrder() > order ? -1 : 0;
    }
}
