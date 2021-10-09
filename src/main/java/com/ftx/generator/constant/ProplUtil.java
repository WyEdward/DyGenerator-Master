package com.ftx.generator.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @author FanJiangFeng
 * @version 1.0.0
 * @ClassName ProplUtil.java
 * @Description TODO 静态方法用来动态获取配置文件的信息的工具类
 * @createTime 2021年05月29日 19:19:00
 */
public class ProplUtil {


    private static Logger logger = LoggerFactory.getLogger(ProplUtil.class);
    //动态获取配置信息
    private static Properties properties;
    static {
        InputStream in = null;
        try {
            properties = new Properties();
            in = ProplUtil.class.getResourceAsStream("application.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String  getProp(String key){
        return properties.getProperty(key);
    }

    public static Properties getConfig(String name){
        Properties props=null;
        try{
            props = new Properties();
            InputStream in = ProplUtil.class.getClassLoader().getResourceAsStream(name);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            props.load(bf);
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return props;
    }

    public static String getPropValue(Properties prop,String key){
        if(key == null || "".equals(key.trim())){
            return null;
        }
        String value = prop.getProperty(key);
        if(value == null){
            return null;
        }
        value = value.trim();
        //判断是否是环境变量配置属性,例如 server.env=${serverEnv:local}
        if(value.startsWith("${") && value.endsWith("}") && value.contains(":")){
            int indexOfColon = value.indexOf(":");
            String envName = value.substring(2,indexOfColon);
            //获取系统环境变量 envName 的内容，如果没有找到，则返回defaultValue
            String envValue = System.getenv(envName);
            if(envValue == null){
                //配置的默认值
                return value.substring(indexOfColon+1,value.length()-1);
            }
            return envValue;
        }
        return value;
    }

    public static void main(String[] args) {
        //配置文件的相对路径 这里是动态获取 因为不确定使用的什么环境
        Properties prop = ProplUtil.getConfig("application.properties");
        //receive 为配置文件中的key
        System.out.println( ProplUtil.getPropValue( prop , "fileUrl" ) );
    }
}
