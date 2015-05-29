package com.twocater.diamond.api.service;

/**
 *
 * @author cpaladin
 */
public interface FilterChain {

    void doFilter(Request request) throws Exception;
}
