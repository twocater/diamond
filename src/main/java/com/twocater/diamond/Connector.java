/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond;

/**
 *
 * @author cpaladin
 */
public interface Connector {

    int getPort();

    void bind() throws Exception;

    void unbind() throws Exception;
}
