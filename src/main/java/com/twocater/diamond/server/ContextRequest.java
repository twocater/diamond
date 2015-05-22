/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.server;

import com.twocater.diamond.api.service.Request;

/**
 *
 * @author cpaladin
 */
public interface ContextRequest extends Request {

    String getFilterPath();

    String mappingService();

    void response() throws Exception;
}
