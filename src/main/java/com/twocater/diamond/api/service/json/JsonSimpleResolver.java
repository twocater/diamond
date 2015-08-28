package com.twocater.diamond.api.service.json;

import org.json.simple.JSONValue;

/**
 * Created by chenzhiwei on 15-8-26.
 */
public class JsonSimpleResolver implements JsonResolver {
    @Override
    public Object parse(String string) throws Exception {
        return JSONValue.parse(string);
    }

    @Override
    public String encode(Object object) throws Exception {
        return JSONValue.toJSONString(object);
    }
}
