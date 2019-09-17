package com.yydcyy.mybatis;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 自定义Mybatis中和数据库交互的核心类
 *  *  它里面可以创建dao接口的代理对象
 *  不定义, 直接实现 myBatis 的, 需要覆写老多方法了
 */
public interface SqlSession {
    /**
     * 根据参数创建一个代理对象
     * @param daoInterfaceClass dao的接口字节码
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> daoInterfaceClass);

    void close();
}
