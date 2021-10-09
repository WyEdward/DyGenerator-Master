##### 快速使用

此处为源码部分，code_generator.rar为源码打包为单机版软件的形式，解压即可使用！

Java代码打包单机版软件参考博客：https://www.cnblogs.com/fantongxue/p/14601328.html

解压code_generator.rar压缩包，里面附有代码生成模板，参照以下说明进行配置即可！

##### 目录结构

- config：包括数据库配置，数据库和java的数据类型映射配置，以及自定义配置
- jre：项目运行环境
- logs：项目运行日志文件
- templates：代码文件模板
- start.bat：启动程序，双击即可打开

##### 使用注意事项
1. ​	模板存放的位置一定要在全英文路径的文件夹下！最好把软件包放置在全英文的路径下就可以直接使用了！
2. 使用前，需要在cofig/application.properties中进行配置！
   1. 数据库连接
   2. 模板文件夹【templates】存放位置
   3. 各个文件的生成位置和全包名
3. 操作界面
   1. 作者：用于生成每个文件的注释信息
   2. 是否覆盖controller：选择是和否来控制是否生成controller文件
   3. 是否进行覆盖：默认是覆盖的，选择不覆盖则会在同级目录生成备份文件，例如：name_bark.java



![](https://img-blog.csdnimg.cn/89ba9900fe314a6c8c78761e4f4c21c5.png)