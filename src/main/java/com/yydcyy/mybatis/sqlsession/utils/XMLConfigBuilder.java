package com.yydcyy.mybatis.sqlsession.utils;

import com.yydcyy.mybatis.annotations.Select;
import com.yydcyy.mybatis.cfg.Configuration;  //自己的 Configuration 方法 , 而不是 myBatis 的, 否则哪里有 setDriver 方法?
import com.yydcyy.mybatis.cfg.Mapper;
import com.yydcyy.mybatis.io.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Method;


/**
 * @author YYDCYY
 * @create 2019-09-17
 *  用于解析 xml 配置文件
 */
public class XMLConfigBuilder {

    /**
     * 解析主配置文件, 把内容填充到 DefaultSqlSession 所需的地方
     *  使用技术 : dom4j + xpath
     * @param config
     * @return
     */
    public static Configuration loadConfiguration(InputStream config){
        try {
            //定义封装连接信息的配置对象（mybatis的配置对象）
            Configuration cfg = new Configuration(); // Configuration 属于 myBatis 包下 command + 鼠标碰着方法, 可以查看具体

            //1.获取SAXReader对象
            SAXReader reader = new SAXReader();

            //2.根据字节输入流获取Document对象
            Document document = reader.read(config);  //需要 try catch

            //3.获取根节点
            Element root = document.getRootElement();

            //4.使用xpath中选择指定节点的方式，获取所有property节点
            List<Element> propertyElements = root.selectNodes("//property");

            //5.遍历节点
            for (Element propertyElement : propertyElements){
            //判断节点是连接数据库的哪部分信息
            //取出name属性的值
                String name = propertyElement.attributeValue("name");
                //表示驱动
                //获取property标签value属性的值
                if ("driver".equals(name))
                    cfg.setDriver(name);

                if ("url".equals(name))
                    cfg.setUrl(name);

                if ("username".equals(name))
                    cfg.setUrl(name);

                if ("password".equals(name))
                    cfg.setPassword(name);
            }

            //取出mappers中的所有mapper标签，判断他们使用了resource还是class属性
            List<Element> mapperElements =  root.selectNodes("//mappers/mapper");
            //遍历集合
            for (Element mapperElement : mapperElements) {
                //判断mapperElement使用的是哪个属性
                Attribute attribute = mapperElement.attribute("resource");

                if (attribute != null) {
                    System.out.println("使用的是 XML ");
                    //表示有resource属性，用的是XML
                    //取出属性的值
                    String mapperPath = attribute.getValue();
                    //把映射配置文件的内容获取出来，封装成一个map
                    Map<String, Mapper> mappers = loadMapperConfiguration(mapperPath);
                    //给configuration中的mappers赋值
                    cfg.setMappers(mappers);
                }else {
                    System.out.println("使用的是注解");
                    //表示没有resource属性，用的是注解
                    //获取class属性的值
                    String daoClassPath = mapperElement.attributeValue("class");

                    //根据daoClassPath获取封装的必要信息
                    Map<String, Mapper> mappers = loadMapperAnnotation(daoClassPath);

                    //给configuration中的mappers赋值
                    cfg.setMappers(mappers);
                }
            }
            //返回Configuration
            return cfg;
        } catch (Exception e) {
           throw new RuntimeException(e);
        } finally {
            try {
                config.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据传入的参数, 解析 XML, 并且封装到 Map 中
     * @param mapperPath 配置映射文件的位置
     * @return
     * map 中包含了获取的唯一标识, key 由 dao 的全限定类名和方法组成
     * 及执行所需的必要信息(value 是一个 Mapper 对象, 里面存放的是执行 sql 语句和要封装的实体类全限定类名
     *
     *
     */
    private static Map<String, Mapper> loadMapperConfiguration(String mapperPath){
        InputStream in = null;
        try {
            //定义返回值对象
            Map<String,Mapper> mappers = new HashMap<>();

            //1.根据路径获取字节输入流
            in = Resources.getResourceAsStream(mapperPath);

            //2.根据字节输入流获取Document对象  用的是 dom4j 包, pom.xml 需要配置
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);

            //3.获取根节点
            Element root = document.getRootElement();

            //4.获取根节点的namespace属性取值
            String namespace = root.attributeValue("namespace");

            //5.获取所有的select节点
            List<Element> selectElements = root.selectNodes("//select");

            //6.遍历select节点集合
            for (Element selectElement : selectElements) {
                //取出id属性的值      组成map中key的部分
                String id = selectElement.attributeValue("id");

                //取出resultType属性的值  组成map中value的部分
                String resultType = selectElement.attributeValue("resultType");

                //取出文本内容            组成map中value的部分
                String queryString = selectElement.getText();

                //创建Key
                String key = namespace + "." + id;

                //创建Value
                Mapper mapper = new Mapper();
                mapper.setQueryString(queryString);
                mapper.setResultType(resultType);

                //把key和value存入mappers中
                mappers.put(key, mapper);
            }
            return mappers;
        } catch (Exception e) {
           throw  new RuntimeException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //catch 中, 执行 throw new RuntimeException(e);
        //到这里才不会 提示需要处理 return
    }

    /**
     * 根据传入的参数，得到dao中所有被select注解标注的方法。
     * 根据方法名称和类名，以及方法上注解value属性的值，组成Mapper的必要信息
     * @param daoClassPath
     * @return
     */
    private static Map<String, Mapper> loadMapperAnnotation(String daoClassPath) throws ClassNotFoundException {
        //定义返回值对象
        Map<String, Mapper> mappers = new HashMap<>();

        //1.得到dao接口的字节码对象
        Class daoClass = Class.forName(daoClassPath);

        //2.得到dao接口中的方法数组
        Method[] methods = daoClass.getMethods();

        //3.遍历Method数组
        for (Method method : methods) {
            //取出每一个方法，判断是否有select注解
            boolean isAnnotated = method.isAnnotationPresent(Select.class);//Selec 需要导 IBatis.annotation 包
            if (isAnnotated) {
                //创建Mapper对象
                Mapper mapper = new Mapper();
                //取出注解的value属性值
                Select selectAnno = method.getAnnotation(Select.class);
                String queryString = selectAnno.value();
                mapper.setQueryString(queryString);

                //获取当前方法的返回值，还要求必须带有泛型信息
                Type type = method.getGenericReturnType();  //Type 需要导入 reflect 包;

                //判断type是不是参数化的类型
                if (type instanceof ParameterizedType) {
                    //强转
                    ParameterizedType ptype = (ParameterizedType) type;

                    //得到参数化类型中的实际类型参数
                    Type[] types = ptype.getActualTypeArguments();

                    //取出第一个
                    Class domainClass = (Class) types[0];

                    //获取domainClass的类名
                    String resultType = domainClass.getName();

                    //给Mapper赋值
                    mapper.setResultType(resultType);
                }

                    //组装key的信息
                    //获取方法的名称
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String key = className + "." + methodName;

                    //给map赋值
                mappers.put(key, mapper);
            }
        }
        return mappers;
    }
}
