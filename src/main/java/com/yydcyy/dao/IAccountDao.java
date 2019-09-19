package com.yydcyy.dao;

import com.yydcyy.domain.Account;
import com.yydcyy.domain.AccountUser;


import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-19
 * * 查询所有账户，同时获取账户的所属用户名称以及它的地址信息
 */
public interface IAccountDao {
   // List<AccountUser> findAll();
    List<Account> findAll();
}
