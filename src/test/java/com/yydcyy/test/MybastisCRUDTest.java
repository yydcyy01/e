package com.yydcyy.test;

import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.QueryVo;
import com.yydcyy.domain.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.Query;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testUpdateUser(){
        // 1 查 id
        User user = userDao.findById(52);

        // 2 更新操作
        user.setAddress("皇后大街东转皇后大街西666");
        int res = userDao.updateUser(user);
        System.out.println("res :  " + res);

        /**
         * id 不存在出异常. 若存在返回 1
         java.lang.NullPointerException
         at com.yydcyy.test.MybastisCRUDTest.testUpdateUser(MybastisCRUDTest.java:85)
         */

    }

    /**
     * 执行结果 res = 1 成功, res = 0 失败
     */
    @Test
    public void testDeleteUser(){
        int res = userDao.deleteUser((49));
        System.out.println("执行结果 :  " + res);
    }

    @Test
    public void testFindByName(){
        // 5 执行任务
        //List<User> users = userDao.findByName("%马%"); // IUserDao.xml  #{username}
        List<User> users = userDao.findByName("马");   // IUserDao.xml   '%${value}%'
        for (User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testFindTotal(){
        int total = userDao.findTotal();
        System.out.println("Total :  " + total);
    }

    //**********************************************
    @Test
    public void testFindByQueryVo(){
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%魔%");
        vo.setUser(user);
        List<User> users = userDao.findByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void tesdFindAll(){
        List<User> users = userDao.findAll();
        for (User u : users) {
            System.out.println(u);
        }
    }


}
