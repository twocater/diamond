package com.twocater.diamond.core.test;

import java.io.UnsupportedEncodingException;

/**
 * Created by chenzhiwei on 15-9-18.
 */
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };
        System.out.println(CONTENT.length);
        System.out.println("Hello World".getBytes("utf-8").length);
    }
}
