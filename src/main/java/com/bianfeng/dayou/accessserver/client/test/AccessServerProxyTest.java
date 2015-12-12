package com.bianfeng.dayou.accessserver.client.test;

import com.bianfeng.dayou.accessserver.client.AccessServerProxy;
import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class AccessServerProxyTest {


    @Test
    public void testLogin() throws IOException {
        AccessServerProxy accessServerProxy = new AccessServerProxy("localhost", 9123, 6000);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("13662536161");
        loginRequest.setPassword("password");
        loginRequest.setGameId("00001");
        loginRequest.setUserType(LoginRequest.UserType.DAYOU_USER);
        LoginResponse loginResponse = accessServerProxy.login(loginRequest);
        System.out.println(loginResponse);
    }
}
