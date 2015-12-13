package com.bianfeng.dayou.accessserver.client.test;

import com.bianfeng.dayou.accessserver.client.AccessServerClient;
import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import com.twocater.diamond.util.ToStringUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/13.
 */
public class AccessServerClientTest {

    public static void main(String[] args) throws IOException {
        AccessServerClient accessServerClient = new AccessServerClient("localhost", 9123, 3000);
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUserName("13662536161");
        loginRequest.setPassword("password");
        loginRequest.setGameId("00001");
        loginRequest.setUserType(LoginRequest.UserType.DAYOU_USER);

        LoginResponse loginResponse = accessServerClient.login(loginRequest);
        System.out.println(ToStringUtil.toString(loginResponse));
    }
}
