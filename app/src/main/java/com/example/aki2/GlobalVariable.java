package com.example.aki2;
import android.app.Application;

public class GlobalVariable extends Application {
    private String Account;     //User 名稱
    private String Passwd;         //User 密碼
    public String getAccount() {
        return Account;
    }
    public void setAccount(String account) {
        this.Account = account;
    }
    public String getPasswd() {
        return Passwd;
    }
    public void setPasswd(String passwd) {
        this.Passwd = passwd;
    }
}