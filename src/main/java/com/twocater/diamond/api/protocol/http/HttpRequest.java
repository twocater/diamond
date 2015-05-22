/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twocater.diamond.api.protocol.http;

/**
 *
 * @author cpaladin
 */
public interface HttpRequest {
 
    String getContentType();
    
    HttpResponse getResponse();
}
