package com.learn.login;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/19 15:01
 */
class UserInfo {
    private String accountId;
    private String userName;

    public UserInfo() {
    }

    public UserInfo(String accountId, String userName) {
        this.accountId = accountId;
        this.userName = userName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
