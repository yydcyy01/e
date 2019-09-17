package com.yydcyy.mybatis.sqlsession.defaluts;



import com.yydcyy.mybatis.cfg.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;

/**
 * @author YYDCYY
 * @create 2019-09-17
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration cfg;
    private Connection connection;

    public DefaultSqlSession(Configuration cfg){
        this.cfg = cfg;
        connection = DataSourceUtil
    }
    @Override
    public void close() {

    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return null;
    }
}
