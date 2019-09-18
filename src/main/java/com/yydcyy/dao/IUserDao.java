package com.yydcyy.dao;

import com.yydcyy.domain.QueryVo;
import com.yydcyy.domain.User;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 用户的持久层接口
 */
public interface IUserDao {

   /**
    * 查询所有
    * @return
    */
   List<User> findAll();

   /**
    * 根据 id 查询
    * @param userId
    * @return
    */
   User findById(Integer userId);

   /**
    * 保存用户
    * @param user
    * @return
    */
   int saveUser(User user);

   /**
    * 更新用户
    * @param user
    * @return
    */
   int updateUser(User user);

   int deleteUser(Integer userId);

   List<User> findByName(String username);

   int findTotal();

   //***********************
   List<User> findByVo(QueryVo vo);


}
