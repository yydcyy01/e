package com.yydcyy.dao;

import com.yydcyy.domain.User;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 用户的持久层接口
 */
public interface IUserDao {
   List<User> findAll();
}
