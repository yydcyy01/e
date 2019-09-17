package com.yydcyy.mybatis.sqlsession.defaluts;



import com.yydcyy.mybatis.SqlSession;
import com.yydcyy.mybatis.cfg.Configuration;
import com.yydcyy.mybatis.sqlsession.proxy.MapperProxy;
import com.yydcyy.mybatis.sqlsession.utils.DataSourceUtil;


import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * SqlSession接口的实现类
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration cfg;
    private Connection connection;

    public DefaultSqlSession(Configuration cfg){
        this.cfg = cfg;
        connection = DataSourceUtil.getConnection(cfg);
    }


/**
 * 用于创建代理对象
 * @param daoInterfaceClass dao的接口字节码
 * @param <T>
 * @re
 */
    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {
        return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),new Class[]{daoInterfaceClass},new MapperProxy(cfg.getMappers(), connection));
    }

    @Override
    public void close() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
