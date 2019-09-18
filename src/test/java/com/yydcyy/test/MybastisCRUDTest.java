package com.yydcyy.test;

import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author YYDCYY
 * @create 2019-09-18
 */
public class MybastisCRUDTest {
    InputStream in;
    SqlSessionFactory factory;
    SqlSession session;
    IUserDao userDao;

    @Before
    public void init() throws IOException {
        //1 读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2 创建构造对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        //3 创建 SqlSession 工厂对象
        factory = builder.build(in);

        //4 创建 SqlSession 对象
        session = factory.openSession();

        //5 创建 Dao 代理对象
        userDao = session.getMapper(IUserDao.class);


    }

    @After
    public  void destory() throws IOException {
        session.commit();

        //7 释放资源
        session.close();
        in.close();
    }

    @Test
    public void testFindOne(){
        //6 执行操作
        User user = userDao.findById(41);
        System.out.println(user);
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setAddress("皇后大街");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setUsername("Remy Yang");

        System.out.println("操作前 :  " + user);
        // 5 执行保存方法
        userDao.saveUser(user);

        System.out.println("操作后 :  " + user);
    }
}