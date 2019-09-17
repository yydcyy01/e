package com.yydcyy.mybatis.sqlsession.utils;

import com.yydcyy.mybatis.cfg.Mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 负责执行SQL语句，并且封装结果集
 */
public class Executor {
    public <E> List<E> selectList(Mapper mapper, Connection conn){
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            //1 取出 mapper 中的数据
            String queryString = mapper.getQueryString(); // select * from user
            String resultType = mapper.getResultType();  // com.yydcyy.domain.User
            Class domainClass = Class.forName(resultType);

            //2 获取 PreparedStatement 对象   直接给给 Exception 异常处理
            pstm = conn.prepareStatement(queryString);

            //3 执行 sql 语句, 获取结果集
            rs = pstm.executeQuery();

            //4 封装结果集
            List<E> list = new ArrayList<>(); // 定义返回值
            while (rs.next()){
                // 实例化封装的实体对象
                E obj = (E) domainClass.newInstance();

                // 取出信息
                ResultSetMetaData rsmd = rs.getMetaData();

                // 列出总数
                int columnCount = rsmd.getColumnCount();

                //遍历总数
                for (int i = 1; i <= columnCount; i ++){
                    //获取每列的名称，列名的序号是从1开始的
                    String columnName = rsmd.getColumnName(i);

                    //根据得到列名，获取每列的值
                    Object columnValue = rs.getObject(columnName);

                    //给obj赋值：使用Java内省机制（借助PropertyDescriptor实现属性的封装）
                    //要求：实体类的属性和数据库表的列名保持一种
                    PropertyDescriptor pd = new PropertyDescriptor(columnName, domainClass);

                    //获取它的写入方法
                    Method writeMethod = pd.getWriteMethod();

                    //把获取的列的值，给对象赋值
                    writeMethod.invoke(obj, columnValue);
                }
                //把赋好值的对象加入到集合中
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            release(pstm, rs);
        }
    }

    //一个关闭, 封装成方法
    private void release(PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstm != null){
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
