package com.yydcyy.domain;

import java.io.Serializable;

/**
 * @author YYDCYY
 * @create 2019-09-18
 */
public class QueryVoUser implements Serializable {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
