package com.ftx.generator.config;

import com.ftx.generator.constant.JsonObject;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * @author FanJiangFeng
 * @version 1.0.0
 * @ClassName Generator.java
 * @Description TODO 代码生成器的核心处理类
 * @createTime 2020年10月31日 11:37:00
 */
public class Generator {

    Logger logger= LoggerFactory.getLogger(Generator.class);

    private String templatePath;//模板路径
    private String outPath;//代码生成路径
    private Configuration cfg;

    public Generator(String outPath)throws Exception {
        this.templatePath=AutoCodeController.codeMap.get("template-path");
        this.outPath = outPath;
        //实例化Configuration对象
        cfg=new Configuration();
        //指定模板加载器
        FileTemplateLoader fileTemplateLoader=new FileTemplateLoader(new File(this.templatePath));
        cfg.setTemplateLoader(fileTemplateLoader);
    }

    /**
     * todo 根据模板路径找到此路径下的所有模板文件 对每个模板进行文件生成
     * @param dataModel
     * @throws Exception
     */
    public String scanAndGenerator(Map<String,Object> dataModel, boolean isNeed,boolean isover, JTextArea msg)throws Exception{
        //根据模板路径找到此路径下的所有模板文件

        List<File> fileList = searchAllFile(new File(this.templatePath));
        try {
            //对每个模板进行文件生成
            for(File file:fileList){
                if(!isNeed){
                    String name = file.getName();
                    if(name.contains("Controller")){
                        continue;
                    }
                }
                //参数1：数据模型  参数2：文件模板
                excuteGenerator(dataModel,file,isover);
            }
            logger.info("代码已生成，请查收！");
            msg.setText("代码已生成，请查收！");
            return "代码已生成";
        } catch (Exception e) {
            e.printStackTrace();
            msg.setText("代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成");
            logger.info("代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成");
            return "代码生成失败，该表不存在，请确认表名是否正确，并且需要手动清理生成失败造成的垃圾文件然后重新生成";
        }

    }


    private void excuteGenerator(Map<String, Object> dataModel, File file,boolean isover) throws Exception {
        //得到模板文件的文件名+后缀名
        String one=file.getAbsolutePath();
        int i = one.indexOf("$");
        String templateFileName = one.substring(i); // ${tableName}Dao.ftl
        //判断该文件生成的模板种类
        int i1 = templateFileName.indexOf("}");
        int i2 = templateFileName.indexOf(".");
        String kindName = templateFileName.substring(i1+1, i2).toLowerCase(); // dao


        String outFileName1 = processString(templateFileName, dataModel);
        String outFileName=changeStr(outFileName1);
        //读取文件模板
        //上面把模板整个文件夹都加载到了模板加载器，所以这里拿模板只需要传入该文件夹下的文件名即可
        Template template = cfg.getTemplate(templateFileName);
        template.setOutputEncoding("utf-8");//指定生成文件的字符集编码
        //存储临时路径
        String templatePath="";

        //创建文件
        File file1=null;
        if("actioncontroller".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("actionController")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("actionController")+File.separator;
        }else if("".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("domain")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("domain")+File.separator;
        }else if("dto".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("DTO")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("DTO")+File.separator;
        }else if("manager".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("manager")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("manager")+File.separator;
        }else if("managerimpl".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("managerImpl")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("managerImpl")+File.separator;
        }else if("mapper".equals(kindName)){
            String substring = templateFileName.substring(templateFileName.lastIndexOf(".") + 1);
            if("xml".equals(substring)){
                file1=mkdir(AutoCodeController.codeMap.get("mapperxml")+File.separator, outFileName);
                templatePath=AutoCodeController.codeMap.get("mapperxml")+File.separator;
            }else{
                file1=mkdir(AutoCodeController.codeMap.get("mapper")+File.separator, outFileName);
                templatePath=AutoCodeController.codeMap.get("mapper")+File.separator;
            }
        }else if("querycontroller".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("querycontroller")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("querycontroller")+File.separator;
        }else if("service".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("service")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("service")+File.separator;
        }else if("serviceimpl".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("serviceImpl")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("serviceImpl")+File.separator;
        }else if("util".equals(kindName)){
            file1=mkdir(AutoCodeController.codeMap.get("domain2DTOUtil")+File.separator, outFileName);
            templatePath=AutoCodeController.codeMap.get("domain2DTOUtil")+File.separator;
        }

        if(!isover){
            //文件不覆盖
            if(file1.exists()){
                String substring = outFileName.substring(0, outFileName.lastIndexOf("."));
                String suffix = outFileName.substring(outFileName.lastIndexOf("."));
                String newFileName=substring+"_bark"+suffix;
                file1.renameTo(new File(templatePath+newFileName));
            }
        }

            //模板处理
            FileWriter fileWriter=new FileWriter(file1);
            template.process(dataModel,fileWriter);
            fileWriter.close();
//            logger.info("--------- "+kindName+"模板已生成----------");

    }

    /**
     * todo ${tableName}Dao.java改为contentDao.java
     * @param templateString
     * @param dataModel
     * @return
     * @throws Exception
     */
    public String processString(String templateString,Map dataModel) throws Exception{
        StringWriter stringWriter=new StringWriter();
        Template template=new Template("ts",new StringReader(templateString),cfg);
        template.process(dataModel,stringWriter);
        return stringWriter.toString();
    }


    //查询整个目录下的所有文件
    public static List<File> searchAllFile(File dir) throws IOException {
        ArrayList arrayList=new ArrayList();
        searchFiles(dir,arrayList);
        return arrayList;

    }

    //递归获取某个目录下的所有文件
    public static void searchFiles(File dir,List<File> collector){
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for(int i=0;i<files.length;i++){
                searchFiles(files[i],collector);
            }
        }else{
            collector.add(dir);
        }
    }

    //创建文件
    public static File mkdir(String dir,String file){
        if(dir==null){
            throw new  IllegalArgumentException("文件夹不许为空");
        }
        File result=new File(dir,file);
        if(result.getParentFile()!=null){
            result.getParentFile().mkdirs();
        }
        return result;
    }

    //首字母大写
    public String upperCase(String str) {
        String temp = str.substring(0, 1).toUpperCase() + str.substring(1);
        return temp;
    }


    //如果包含_  则高峰写法
    public static String changeStr(String str){
        if(str.indexOf("_")!=-1){
            int i = str.indexOf("_");
            String one = str.substring(0, i);
            one = one.substring(0, 1).toUpperCase() + one.substring(1);
            String two = str.substring(i + 1, str.length());
            two = two.substring(0, 1).toUpperCase() + two.substring(1);
              str=one+two;
            if(str.indexOf("_")!=-1) {
                 str = changeStr(str);
            }
        }
        return str;
    }




}
