package com.yydcyy.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.User;
import com.yydcyy.mybatis.io.Resources;
import com.yydcyy.mybatis.sqlsession.SqlSession;
import com.yydcyy.mybatis.sqlsession.SqlSessionFactory;
import com.yydcyy.mybatis.sqlsession.SqlSessionFactoryBuilder;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * mybatis demo 案例
 */
public class MybatisTest {
    public static void main(String[] args) throws IOException {
        //1 读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2 创建 SqlSessionFactory
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in); // 不省写, 生成 in 对象,最后in.close();

        //3 使用工厂生产 SqlSession 对象
        SqlSession session = factory.openSession();

        //4 使用 sqlSession 创建 Dao 接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);

        /**
         * 一般工程直接使用上面 session.getMapper(类.class); 不需要 创建 dao.impl->UserDaoImp
         * 为了知道工作原理, 实现 UserDaoImpl 方法, 执行 session 加载等等
         */
        //IUserDao userDao  = new UserDaoImpl(factory);

        //5 使用代理对象执行方法
         List<User> users = userDao.findAll();

        //返回结果打印
        for (User user : users){
            System.out.println(user);
        }

        //6 释放资源
        session.close();
        in.close();
    }
}
