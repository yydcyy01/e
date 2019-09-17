package com.yydcyy.mybatis.sqlsession.defaluts;


import com.yydcyy.mybatis.SqlSession;
import com.yydcyy.mybatis.SqlSessionFactory;
import com.yydcyy.mybatis.cfg.Configuration;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * SqlSessionFactory接口的实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration cfg;

    public DefaultSqlSessionFactory(Configuration cfg){
        this.cfg = cfg;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(cfg);
    }

}
