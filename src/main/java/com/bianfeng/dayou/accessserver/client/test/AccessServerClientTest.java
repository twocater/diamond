package com.bianfeng.dayou.accessserver.client.test;

import com.bianfeng.dayou.accessserver.client.AccessServerClient;
import com.bianfeng.dayou.accessserver.client.AccessServerResponse;
import com.bianfeng.dayou.accessserver.client.LongSocketIO;
import com.bianfeng.dayou.accessserver.client.request.LoginRequest;
import com.bianfeng.dayou.accessserver.client.response.LoginResponse;
import com.twocater.diamond.util.ToStringUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/13.
 */
public class AccessServerClientTest {

    public static void main(String[] args) throws IOException {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("13662536161");
        loginRequest.setPassword("password");
        loginRequest.setGameId("00001");
        loginRequest.setUserType(LoginRequest.UserType.DAYOU_USER);


        final AccessServerClient accessServerClient = new AccessServerClient("localhost", 9123, 0);
        while(!accessServerClient.isConnected()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        accessServerClient.login(loginRequest);
                        Thread.sleep(10000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        AccessServerResponse accessServerResponse = accessServerClient.readResponse();
//                        System.out.println(ToStringUtil.toString(accessServerClient.toLoginResponse(accessServerResponse)));
//                        Thread.sleep(1000);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

    }
}
