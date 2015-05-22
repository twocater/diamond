/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.protocol.http;

import com.twocater.diamond.server.AbstractContext;
import com.twocater.diamond.server.ContextRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cpaladin
 */
abstract class AbstractContextRequest implements ContextRequest{

    protected Map<String, Object> attributes = new HashMap<String, Object>();
    protected AbstractContext context;

    public AbstractContextRequest(AbstractContext context) {
        this.context = context;
    }

}
