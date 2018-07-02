package com.wtgroup.demo.springcache;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/7 14:44
 */

public class Account {
    private String userName;
    private String password;
    private Double money;

    public Account(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Account setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public Double getMoney() {
        return money;
    }

    public Account setMoney(Double money) {
        this.money = money;
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                '}';
    }
}
