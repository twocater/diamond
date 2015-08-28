/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.service.http;

import com.twocater.diamond.api.protocol.http.HttpRequest;
import com.twocater.diamond.api.service.json.JsonEntry;

/**
 *
 * @author cpaladin
 */
public interface HttpJsonRequest extends HttpRequest, JsonEntry {
    // 回复数据
    HttpJsonResponse getResponse();
}
