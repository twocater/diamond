package com.bianfeng.dayou.accessserver.client;

import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import com.bianfeng.dayou.accessserver.client.util.SocketUtil;

import java.io.IOException;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class AccessServerProxy {
    private String ip;
    private int port;
    private int timeout;

    public AccessServerProxy(String ip, int port, int timeout) {
        this.ip = ip;
        this.port = port;
        this.timeout = timeout;
    }

    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        AccessServerRequest serverRequest = new AccessServerRequest();
        serverRequest.setCommand((byte) 1);
        serverRequest.setParam("u", loginRequest.getUserName());
        serverRequest.setParam("p", loginRequest.getPassword());
        serverRequest.setParam("t", loginRequest.getUserType() + "");
        serverRequest.setParam("g", loginRequest.getGameId());

        AccessServerResponse serverResponse = sendRequest(serverRequest);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(serverResponse.getResult());
        loginResponse.setSuccess(loginResponse.getResult() == 1);
        loginResponse.setUserName(serverResponse.getParam("u"));
        loginResponse.setNickName(serverResponse.getParam("n"));
        return loginResponse;


    }

    private AccessServerResponse sendRequest(AccessServerRequest serverRequest) throws IOException {
        byte[] requestBytes = AccessServerCodec.encode(serverRequest);
        byte[] bytes = SocketUtil.sendShortLinkRequest(ip, port, timeout, requestBytes);
        return AccessServerCodec.decode(bytes);
    }

}
