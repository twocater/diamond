package com.bianfeng.dayou.accessserver;

import java.nio.charset.StandardCharsets;

/**
 * Created by Administrator on 2015/12/7.
 */
public class NettyMessage {
    private byte version;
    private byte encrypt;
    private byte command;
    private byte[] content;

    public byte getVersion() {
        return version;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public short getCommand() {
        return command;
    }

    public byte[] getContent() {
        return content;
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

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version:").append(version).append(",");
        sb.append("encrypt:").append(encrypt).append(",");
        sb.append("command:").append(command).append(",");
        sb.append("content:").append(new String(content, StandardCharsets.UTF_8));
        return sb.toString();
    }
}
