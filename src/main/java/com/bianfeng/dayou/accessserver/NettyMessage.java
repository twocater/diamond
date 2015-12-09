package com.bianfeng.dayou.accessserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/7.
 */
public class NettyMessage {
    private byte version;
    private byte encrypt;
    private byte longConnection;
    private byte command;
    private byte[] data;

    private Map<String, String> params = new HashMap<String, String>();

    public byte getLongConnection() {
        return longConnection;
    }

    public byte getVersion() {
        return version;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public byte getCommand() {
        return command;
    }


    public void setLongConnection(byte longConnection) {
        this.longConnection = longConnection;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public byte[] getRawMessage() {
        return null;
    }

    public void setParams(String paramName, String paramValue) {
        this.params.put(paramName, paramValue);
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version:").append(version).append(",");
        sb.append("encrypt:").append(encrypt).append(",");
        sb.append("longConnection:").append(longConnection).append(",");
        sb.append("command:").append(command).append(",");
        return sb.toString();
    }

}
