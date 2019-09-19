package com.yydcyy.test;

import com.yydcyy.dao.IAccountDao;
import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.Account;
import com.yydcyy.domain.AccountUser;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-19
 * 一对多账户的操作
 */
public class AccountTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        //1 读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2 创建构造对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        //3 创建 SqlSession 工厂对象
        factory = builder.build(in);

        //4 创建 SqlSession 对象
        //session = factory.openSession();
        session = factory.openSession(true);

        //5 创建 Dao 代理对象
        accountDao = session.getMapper(IAccountDao.class);


    }

    @After
    public  void destory() throws IOException {
        session.commit();

        //7 释放资源
        session.close();
        in.close();
    }

    @Test
    public void testFindAll(){
       // List<AccountUser> accountusers = accountDao.findAll();
        List<Account> accountusers = accountDao.findAll();
        for (Account au : accountusers){
            System.out.println(au);
            System.out.println(au.getUser());
        }
    }
}
