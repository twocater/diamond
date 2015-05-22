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
public enum HttpHeader {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    USER_AGENT("User-Agent"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    SERVER("Server"),
    CONNECTION("Connection"),
    HOST("Host"),
    COOKIE("Cookie"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    SET_COOKIE("Set-Cookie"),
    DATE("Date");

    private String name;

    HttpHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
