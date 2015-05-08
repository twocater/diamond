/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server.parse;

import java.util.List;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
public class ContextServiceConfig implements Comparable<ContextServiceConfig> {

    private String serviceName;
    private String serviceClass;
    private int order;
    private Map<String, String> params;
    private List<String> servicePaths;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public List<String> getServicePaths() {
        return servicePaths;
    }

    public void setServicePaths(List<String> servicePaths) {
        this.servicePaths = servicePaths;
    }

    @Override
    public int compareTo(ContextServiceConfig o) {
        if (order == 0) {
            return o.getOrder() > 0 ? 1 : 0;
        }
        if (o.getOrder() == 0) {
            return -1;
        }
        return o.getOrder() > order ? -1 : 0;
    }

}
