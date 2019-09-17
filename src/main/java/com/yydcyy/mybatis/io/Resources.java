package com.yydcyy.mybatis.io;

import java.io.InputStream;

/**
 * @author YYDCYY
 * @create 2019-09-17
 * 使用类加载器读取配置文件的类
 */
public class Resources {
    /**
     * 由传入的 String 参数, 获取一个字节输入流
     * @param filePath
     * @return
     */
    public static InputStream getResourceAsStream(String filePath){
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
