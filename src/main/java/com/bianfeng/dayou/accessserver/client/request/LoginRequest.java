package com.bianfeng.dayou.accessserver.client.request;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class LoginRequest {
    private String userName;
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
