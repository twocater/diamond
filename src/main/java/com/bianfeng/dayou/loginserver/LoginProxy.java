package com.bianfeng.dayou.loginserver;

/**
 * Created by chenzhiwei on 15-12-9.
 */
public class LoginProxy {
    public static final int SUCCESS = 1;

    public static LoginResult login(byte[] message) {
        LoginResult loginResult = new LoginResult();

        loginResult.setResult(SUCCESS);
        loginResult.setSuccess(true);
        return loginResult;
    }
}
