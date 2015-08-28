package com.twocater.diamond.api.service.json;


/**
 * Created by chenzhiwei on 15-8-26.
 */
public interface JsonResolver {


    public Object parse(String string) throws Exception;

    public String encode(Object object) throws Exception;
}
