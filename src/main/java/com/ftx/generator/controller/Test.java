package com.ftx.generator.controller;

import com.ftx.generator.config.AutoCodeController;
import com.ftx.generator.constant.JsonObject;
import com.ftx.generator.constant.ProplUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author FanJiangFeng
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2021年05月28日 15:11:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    public static void begin(String tableName,boolean result1,boolean result2,JTextArea msg,String author) throws Exception {
        AutoCodeController.autoCreateCode(tableName, result1,result2,msg,author);

    }

    public static void main(String[] args) throws Exception {
        //第一个弹框
        JFrame jFrame=new JFrame("E2E电商平台代码生成器");
        //设置流动布局
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        jFrame.setSize(400,300);
        jFrame.setLocation(700,300);
        //显示
        jFrame.setVisible(true);
        //设置关闭方式（点 X）
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //标题
        JLabel jLabel=new JLabel("数据库表：");
        JLabel jLabet=new JLabel("作者：");
        //输入框
        JTextField tableName=new JTextField("",34);
        JTextField author=new JTextField("",34);


        //单选框，是否需要生成controller
        JLabel jLabel2=new JLabel("是否需要生成Controller？");
        JRadioButton jRadioButton1=new JRadioButton("是");
        JRadioButton jRadioButton2=new JRadioButton("否");
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        JLabel jLabel3=new JLabel("文件若存在是否进行覆盖？");
        JRadioButton jRadioButton3=new JRadioButton("是");
        JRadioButton jRadioButton4=new JRadioButton("否");
        ButtonGroup buttonGroup1=new ButtonGroup();
        buttonGroup1.add(jRadioButton3);
        buttonGroup1.add(jRadioButton4);

        //按钮
        JButton button=new JButton("开始生成代码");
        //添加到jFrame中
        jFrame.add(jLabel);
        jFrame.add(tableName);
        jFrame.add(jLabet);
        jFrame.add(author);
        jFrame.add(jLabel2);
        jFrame.add(jRadioButton1);
        jFrame.add(jRadioButton2);
        jFrame.add(jLabel3);
        jFrame.add(jRadioButton3);
        jFrame.add(jRadioButton4);

        jFrame.add(button);

        JTextArea msg=new JTextArea(3,33);
        msg.setLineWrap(true);
        msg.setText("完善信息，准备生成代码。。。");
        msg.setEnabled(false);
        msg.setFont(new java.awt.Font("Dialog", 1, 15));
        jFrame.add(msg);

        //大小不可变
        jFrame.setResizable(false);
        button.setSize(440,20);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tableName.getText().trim();//数据库表

                boolean selected = jRadioButton1.isSelected();
                boolean selected1 = jRadioButton2.isSelected();
                boolean selected2 = jRadioButton3.isSelected();
                boolean selected3 = jRadioButton4.isSelected();

                if("".equals(text)||"".equals(author.getText().trim())){
                    msg.setText("请填写信息");
                }else if(!(selected || selected1) || !(selected2 || selected3)){
                    msg.setText("请选择必填项内容");
                }else{
                    msg.setText("开始生成代码中，请稍后。。。");
                    try {
                        begin(text,selected,selected2,msg,author.getText().trim());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }
}
