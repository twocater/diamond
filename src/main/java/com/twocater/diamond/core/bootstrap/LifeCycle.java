/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.core.bootstrap;

/**
 *
 * @author cpaladin
 */
public interface LifeCycle {

    void init() throws Exception;

    void start() throws Exception;

    void stop() throws Exception;

    void destroy() throws Exception;
}
