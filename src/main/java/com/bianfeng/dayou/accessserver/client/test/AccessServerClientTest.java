package com.bianfeng.dayou.accessserver.client.test;

import com.bianfeng.dayou.accessserver.client.AccessServerClient;
import com.bianfeng.dayou.accessserver.client.AccessServerResponse;
import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import com.twocater.diamond.util.ToStringUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/13.
 */
public class AccessServerClientTest {

    public static void main(String[] args) throws IOException {
        final AccessServerClient accessServerClient = new AccessServerClient("localhost", 9123, 0);
        final LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUserName("13662536161");
        loginRequest.setPassword("password");
        loginRequest.setGameId("00001");
        loginRequest.setUserType(LoginRequest.UserType.DAYOU_USER);


        accessServerClient.login(loginRequest);
        AccessServerResponse accessServerResponse = accessServerClient.readResponse();
        LoginResponse loginResponse = accessServerClient.toLoginResponse(accessServerResponse);
        System.out.println(ToStringUtil.toString(loginResponse));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        accessServerClient.login(loginRequest);
                        Thread.sleep(3000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    accessServerClient.receiveMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.in.read();
    }
}
