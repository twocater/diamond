package com.bianfeng.dayou.accessserver.client.request;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class LoginRequest {
    public interface UserType {
        public static final int DAYOU_USER =1;
        public static final int WX_USER = 2;
        public static final int VISITOR_USER = 3;
    }

    private String userName;
    private String password;
    private int userType;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
