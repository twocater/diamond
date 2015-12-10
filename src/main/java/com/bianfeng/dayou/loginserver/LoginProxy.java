package com.bianfeng.dayou.loginserver;

/**
 * Created by chenzhiwei on 15-12-9.
 */
public class LoginProxy {
    public static final int SUCCESS = 1;

    public static LoginResponse login(byte[] message) {
        LoginResponse response = new LoginResponse();

        response.setResult(SUCCESS);
        response.setSuccess(true);
        response.setUserName("fffffuck");
        response.setNickName("小小小．．．");
        return response;
    }
}
