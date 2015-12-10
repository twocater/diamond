package com.bianfeng.dayou.accessserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class ServerResponse {
    private byte version;
    private byte encrypt;
    private byte longConnection;
    private byte result;
    private Map<String, String> params = new HashMap<String, String>();

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public byte getLongConnection() {
        return longConnection;
    }

    public void setLongConnection(byte longConnection) {
        this.longConnection = longConnection;
    }

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParams(String paramName, String paramValue) {
        this.params.put(paramName, paramValue);
    }
}
