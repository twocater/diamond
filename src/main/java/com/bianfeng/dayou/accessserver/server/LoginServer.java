package com.bianfeng.dayou.accessserver.server;

import com.bianfeng.dayou.loginserver.LoginProxy;
import com.bianfeng.dayou.loginserver.LoginResponse;

/**
 * Created by chenzhiwei on 15-12-9.
 */
public class LoginServer {

    public static LoginResponse login(byte[] message) {
        LoginResponse loginResult = LoginProxy.login(message);
        return loginResult;
    }
}
