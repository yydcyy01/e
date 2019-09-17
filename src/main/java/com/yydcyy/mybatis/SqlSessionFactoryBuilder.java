package com.yydcyy.mybatis;

import com.yydcyy.mybatis.cfg.Configuration;
import com.yydcyy.mybatis.sqlsession.defaluts.DefaultSqlSessionFactory;
import com.yydcyy.mybatis.sqlsession.utils.XMLConfigBuilder;
import org.apache.ibatis.session.SqlSessionFactory;


import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 用于创建一个SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(InputStream config){
        Configuration cfg = XMLConfigBuilder.loadConfiguration(config);
        return new DefaultSqlSessionFactory(cfg);
    }
}
