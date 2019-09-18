package com.yydcyy.dao.impl;

import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 校验 MyBatis 实现功能. 实际开发不需要
 */
public class UserDaoImpl /*implements IUserDao */{
    /*private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        //1 使用工厂创建 SqlSession 对象
        SqlSession session = factory.openSession();

        //2 使用 Session 查询所有方法
        List<User> users = session.selectList("com.yydcyy.dao.IUserDao.findAll");
        session.close();

        //3 返回查询结果
        return users;
    }

    @Override
    public User findById(Integer userId) {
        return null;
    }*/
}
