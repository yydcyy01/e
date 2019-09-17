package com.yydcyy.mybatis;

/**
 * @author YYDCYY
 * @create 2019-09-17
 */
public interface SqlSessionFactory {
    /**
     * 用于打开一个新的SqlSession对象
     * @return
     */
    SqlSession openSession();
}
