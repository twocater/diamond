package com.twocater.diamond.core.server.parse;

/**
 * Created by chenzhiwei on 15-9-6.
 */
public class ContextListenerConfig implements Comparable<ContextListenerConfig> {
    private String listenerClass;
    private int order;

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(ContextListenerConfig o) {
        if (order == 0) {
            return o.getOrder() > 0 ? 1 : 0;
        }
        if (o.getOrder() == 0) {
            return -1;
        }
        return o.getOrder() > order ? -1 : 0;
    }
}
