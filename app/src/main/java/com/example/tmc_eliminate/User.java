package com.example.tmc_eliminate;

public class User {
    private String account;
    private String password;
    private int ecount_s=0;
    private int mcount_s=0;
    private int dcount_s=0;
    private int ecount_f=0;
    private int mcount_f=0;
    private int dcount_f=0;
    private int min_time=0;


    public User(String account, String password){
        this.account=account;
        this.password=password;
    }
    public User(String account,String password,int ecount_s,int mcount_s,int dcount_s,int ecount_f,int mcount_f,int dcount_f,int min_time){
        this(account,password);
        this.ecount_s=ecount_s;
        this.mcount_s=mcount_s;
        this.dcount_s=dcount_s;
        this.ecount_f=ecount_f;
        this.mcount_f=mcount_f;
        this.dcount_f=dcount_f;
        this.min_time=min_time;
    }


    public String getAccount(){
        return this.account;
    }
    public void setAccount(String account){
        this.account=account;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public int getEcount_s() {
        return ecount_s;
    }

    public void setEcount_s(int ecount_s) {
        this.ecount_s = ecount_s;
    }

    public int getMcount_s() {
        return mcount_s;
    }

    public void setMcount_s(int mcount_s) {
        this.mcount_s = mcount_s;
    }

    public int getDcount_s() {
        return dcount_s;
    }

    public void setDcount_s(int dcount_s) {
        this.dcount_s = dcount_s;
    }

    public int getEcount_f() {
        return ecount_f;
    }

    public void setEcount_f(int ecount_f) {
        this.ecount_f = ecount_f;
    }

    public int getMcount_f() {
        return mcount_f;
    }

    public void setMcount_f(int mcount_f) {
        this.mcount_f = mcount_f;
    }

    public int getDcount_f() {
        return dcount_f;
    }

    public void setDcount_f(int dcount_f) {
        this.dcount_f = dcount_f;
    }

    public int getMin_time() {
        return min_time;
    }

    public void setMin_time(int min_time) {
        this.min_time = min_time;
    }


    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", ecount_s=" + ecount_s +
                ", mcount_s=" + mcount_s +
                ", dcount_s=" + dcount_s +
                ", ecount_f=" + ecount_f +
                ", mcount_f=" + mcount_f +
                ", dcount_f=" + dcount_f +
                ", min_time=" + min_time +
                '}';
    }
}
