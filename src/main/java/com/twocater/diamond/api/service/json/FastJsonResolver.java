package com.twocater.diamond.api.service.json;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chenzhiwei on 15-8-26.
 */
public class FastJsonResolver implements JsonResolver {
    @Override
    public Object parse(String text) throws Exception {
        return JSONObject.parse(text);
    }

    @Override
    public String encode(Object object) throws Exception {
        return JSONObject.toJSONString(object);
    }
}
