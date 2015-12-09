package com.bianfeng.dayou.accessserver.server;

import com.bianfeng.dayou.loginserver.LoginProxy;
import com.bianfeng.dayou.loginserver.LoginResult;

/**
 * Created by chenzhiwei on 15-12-9.
 */
public class LoginServer {

    public static LoginResult login(byte[] message) {
        LoginResult loginResult = LoginProxy.login(message);
        return loginResult;
    }
}
