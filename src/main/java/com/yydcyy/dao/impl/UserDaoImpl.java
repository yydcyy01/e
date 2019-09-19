package com.yydcyy.dao.impl;

import com.yydcyy.dao.IUserDao;
import com.yydcyy.domain.QueryVo;
import com.yydcyy.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 校验 MyBatis 实现功能.
 * 传统开发方式
 * (另一种是映射方式, 只需要写 interface xxx文件, 在写 xxx.xml,  再写 SqlMapConfig.xml 方法即可 )
 */
public class UserDaoImpl /*implements IUserDao*/ {
  /*  private SqlSessionFactory factory;

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
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.yydcyy.dao.IUserDao.findById", userId);
        return user;
    }

    @Override
    public int saveUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(Integer userId) {
        return 0;
    }

    @Override
    public List<User> findByName(String username) {
        return null;
    }

    @Override
    public int findTotal() {
        return 0;
    }

    @Override
    public List<User> findByVo(QueryVo vo) {
        return null;
    }*/
}
