/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.service;

/**
 *
 * @author cpaladin
 */
public interface Filter {

    void init(FilterConfig config) throws Exception;

    void doFilter(Request request, FilterChain chain) throws Exception;

    void destroy();
}
