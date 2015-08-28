package com.twocater.diamond.api.service.json;

import java.io.UnsupportedEncodingException;

/**
 * Created by chenzhiwei on 15-8-26.
 */
public class JsonSupport {
    public static final String DATA_ENCODING = "UTF-8";

    private static JsonResolver jsonResolver = new JsonSimpleResolver();

    public static Object parse(byte[] data, String encode) throws Exception {
        if (encode == null || encode.isEmpty()) {
            encode = DATA_ENCODING;
        }

        return jsonResolver.parse(new String(data, encode));
    }

    public static byte[] encode(Object object, String encode) throws Exception {
        if (encode == null || encode.isEmpty()) {
            encode = DATA_ENCODING;
        }

        return jsonResolver.encode(object).getBytes(encode);
    }
}
