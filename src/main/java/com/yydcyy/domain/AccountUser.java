package com.yydcyy.domain;

import java.io.Serializable;

/**
 * @author YYDCYY
 * @create 2019-09-19
 */
public class AccountUser extends Account implements Serializable {
    String username;
    String address;


    //这个一对多 String 复写的有意思, 注意点
    @Override
    public String toString() {
        return super.toString() + "AccountUser{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
