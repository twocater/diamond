package com.bianfeng.dayou.accessserver.client.response;

/**
 * Created by chenzhiwei on 15-12-10.
 */
public class LoginResponse {
    private int result;
    private boolean success;
    private String userName;
    private String nickName;


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("result:").append(result).append(",");
        sb.append("success:").append(success).append(",");
        sb.append("userName:").append(userName).append(",");
        sb.append("nickName:").append(nickName).append(",");
        return sb.toString();
    }
}
