package com.bianfeng.dayou.gameloginserver.client.request;

/**
 * Created by Administrator on 2015/12/12.
 */
public class GameLoginRequest {
    private String userName;
    private String password;
    private String userType;
    private String gameId;

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


}
