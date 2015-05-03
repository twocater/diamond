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
public abstract class HttpService<T extends HttpRequest> extends Service<T> {

    @Override
    public abstract void service(T request) throws Exception;

}
