package com.nihon.aki2.control;
import android.app.Application;

public class GlobalVariable extends Application {
    private String Account;     //User 名稱
    private String Passwd;         //User 密碼
    private String names;

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    private double dollar;
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}