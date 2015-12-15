package com.bianfeng.dayou.accessserver.client;

import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import com.twocater.diamond.util.ToStringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/13.
 */
public class AccessServerClient {

    private String ip;
    private int port;
    private int timeout;
    private LongSocketIO longSocketIO;
    private boolean isLoginSuccess;
    private Thread thread;


    public AccessServerClient(final String ip, final int port, final int timeout) throws IOException {
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;

        longSocketIO = new LongSocketIO(ip, port, timeout);
    }

    public boolean isConnected() {
        return longSocketIO.isConnected();
    }


    public void login(LoginRequest loginRequest) throws IOException {
        AccessServerRequest serverRequest = toAccessServerRequest(loginRequest);
        serverRequest.setLongConnection((byte) 1);
        byte[] sendData = AccessServerCodec.encode(serverRequest);
        longSocketIO.nioWrite(sendData);
        System.out.println("write ok");
    }

    private AccessServerRequest toAccessServerRequest(LoginRequest loginRequest) {
        AccessServerRequest serverRequest = new AccessServerRequest();
        serverRequest.setCommand((byte) 1);
        serverRequest.setParam("u", loginRequest.getUserName());
        serverRequest.setParam("p", loginRequest.getPassword());
        serverRequest.setParam("t", loginRequest.getUserType() + "");
        serverRequest.setParam("g", loginRequest.getGameId());
        return serverRequest;
    }

    public static LoginResponse toLoginResponse(AccessServerResponse accessServerResponse) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(accessServerResponse.getResult());
        loginResponse.setSuccess(loginResponse.getResult() == 1);
        loginResponse.setUserName(accessServerResponse.getParam("u"));
        loginResponse.setNickName(accessServerResponse.getParam("n"));
        return loginResponse;
    }


    public AccessServerResponse readResponse() throws IOException {
        byte[] header;
        byte[] body;
        header = longSocketIO.readBytesFully(4);
        short dataLength = (short) (header[2] & 0xff << 8 | header[3] & 0xff);
        body = longSocketIO.readBytesFully(dataLength);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(header, body);
        AccessServerResponse accessServerResponse = AccessServerCodec.decode(byteBuf);
        return accessServerResponse;
    }
}
