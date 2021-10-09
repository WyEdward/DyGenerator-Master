package com.ftx.generator.config;

import com.ftx.generator.constant.ProplUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author FanJiangFeng
 * @version 1.0.0
 * @ClassName AutoCodeController.java
 * @Description TODO
 * @createTime 2020年10月30日 18:43:00
 */
@RestController
public class AutoCodeController {

     static Logger logger= LoggerFactory.getLogger(AutoCodeController.class);

    public static Map<String,String> customMap=new HashMap<>();
    public static Map<String,String> dbMap=new HashMap<>();
    public static Map<String,String> codeMap=new HashMap<>();

    //静态块，预加载，将自定义的配置文件properties的内容全部加载到customMap中
    static {
        //配置文件的相对路径 这里是动态获取 因为不确定使用的什么环境
        //这种方式打包之后就获取不到配置文件了
//        Properties prop = ProplUtil.getConfig("config/application.properties");

        //打包之后获取jar包同级目录下config的配置使用这个
         Properties prop = getProps("application.properties");
         if(prop.size()==0){
             prop = ProplUtil.getConfig("application.properties");
         }

            customMap.putAll((Map)prop);
            dbMap.putAll((Map)prop);
            codeMap.putAll((Map)prop);

    }

    public static String autoCreateCode(String tableName, boolean isNeed,boolean isover, JTextArea msg,String author) throws Exception {
        msg.setText("尝试获取数据库连接....");
        logger.info("尝试获取数据库连接....");

        //获取连接
        ResultSet columns = null;
        List<Column> columnList= null;
        DatabaseMetaData metaData = null;
        try {
            Class.forName(dbMap.get("dbDriverName"));
            Connection connection = DriverManager.getConnection(dbMap.get("dbUrl"), dbMap.get("dbUserName"), dbMap.get("dbPassword"));
            //获取元数据
            columns = null;
            columnList = null;

             metaData = connection.getMetaData();

            columns = metaData.getColumns(null, null, tableName, null);
            columnList = new ArrayList<>();
            logger.info("连接数据库成功，正在生成代码。。。");
            msg.setText("连接数据库成功");
        } catch (Exception e) {
            msg.setText("数据库连接失败，请检查网络连接和数据库配置");
        }

        //主键存放的集合
        List<String> keyList=new ArrayList<>();
        //获取主键
        try {
            ResultSet primaryKeys = metaData.getPrimaryKeys(dbMap.get("dbName"),"", tableName);
            //对主键遍历的原因（或许一张表有多个主键）
            String keys="";
            while (primaryKeys.next()){
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys+=keyName+",";
            }
            if(keys.contains(",")){
                String substring = keys.substring(0, keys.lastIndexOf(","));
                keyList = Arrays.asList(substring.split(","));
            }
        } catch (SQLSyntaxErrorException e) {
            msg.setText("解析主键时发生错误，确认表名是否正确");
            return "解析主键时发生错误，确认表名是否正确";
        }

        boolean state=false;
        int index=0;
        while (columns.next()){
            state=true;
            Column column=new Column();
            //列名称
            String db_column_name = columns.getString("COLUMN_NAME");
            //判断是否是主键
            if(keyList.size()>0 && keyList.contains(db_column_name)){
                column.setColumnKey(true);
            }else{
                column.setColumnKey(false);
            }
            //备注
            String remarks = columns.getString("REMARKS");
            if(remarks!=null && !"".equals(remarks)){
                column.setColumnComment(remarks);
            }else{
                column.setColumnComment("（表未设置注释）");
            }

            index=1;
            //java实体的属性名
            String java_column_name = Generator.changeStr(db_column_name.toLowerCase());
            java_column_name = java_column_name.substring(0, 1).toLowerCase() + java_column_name.substring(1);
            //数据库类型
            String db_type = columns.getString("TYPE_NAME");
            //java类型
            String java_type=customMap.get(db_type);

            column.setColumnName(db_column_name.toLowerCase());
            column.setColumnName2(java_column_name);
            column.setColumnDbType(db_type);
            column.setColumnType(java_type);
            boolean isHave=false;
            for(Column column1:columnList){
                if(column1.getColumnName().equals(db_column_name.toLowerCase())){
                   isHave=true;
                   break;
                }
            }
            if(!isHave){
                columnList.add(column);
            }
        }

        columns.close();
        if(!state){
            logger.info("代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成");
            msg.setText("代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成");
            return "代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成";
        }
        //对table进行代码生成
        Map<String, Object> dataModel = getDataModel(columnList,tableName);
        dataModel.put("title",Generator.changeStr(tableName));
        dataModel.put("author",author);
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = sdf.format(date);
        dataModel.put("time",time);
//        Map config = getConfig(request);
        Generator generator=new Generator("");
        return generator.scanAndGenerator(dataModel,isNeed,isover,msg);

    }

    /**
    * @Description 代码自动生成
    * @Date 2020/10/31 11:26
    * @return
    * @Author FanJiangFeng
    * @Version1.0
    * @History
    */



    private static Map<String, Object> getDataModel(List<Column> columnList,String tableName) {
        String packageName = codeMap.get("package-name");
        Map<String,Object> map=new HashMap<>();
        //自定义配置
        map.putAll(customMap);
        //元数据
        map.put("table",columnList);
        map.put("pPackage",packageName);
        map.put("tableName",tableName);
        map.put("queryPackage",codeMap.get("queryPackage"));
        map.put("actionPackage",codeMap.get("actionPackage"));
        map.put("servicePackage",codeMap.get("servicePackage"));
        map.put("serviceImplPackage",codeMap.get("serviceImplPackage"));
        map.put("managerPackage",codeMap.get("managerPackage"));
        map.put("managerImplPackage",codeMap.get("managerImplPackage"));
        map.put("mapperPackage",codeMap.get("mapperPackage"));
        map.put("domainPackage",codeMap.get("domainPackage"));
        map.put("domainUtilPackage",codeMap.get("domainUtilPackage"));
        map.put("DTOPackage",codeMap.get("DTOPackage"));
        return map;

    }

    private static Properties getProps(String fileName){

        Properties properties = new Properties();
        //读取当前 jar 包存放的路径
        String path = System.getProperty("user.dir");
//        System.out.println(path);
        //判断文件是否存在
        InputStream input = null;
        try {
            //读取当前文件夹下config文件目录下的配置文件
            input = new FileInputStream(new File(path+ File.separator+"config"+File.separator+fileName));
        } catch (FileNotFoundException e) {
            System.out.println("jdbc.properties not find. " + e);
        }
        if (input != null) {
            try {
                properties.load(input);
            } catch (IOException e) {
                System.out.println("fail to load the jdbc.properties," + e);
            }
        }
        return properties;
    }

}
