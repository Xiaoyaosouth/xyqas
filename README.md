# 逍遥论坛系统

码云仓库
https://gitee.com/xiaoyaozainanfang/xyqas
<br>
GitHub仓库
https://github.com/Xiaoyaosouth/xyqas

#### 介绍
【使用的技术】<br>
数据库：MySQL 5.7<br>
数据库连接池：c3p0（版本0.9.5.2）<br>
后端：Spring + SpringMVC + MyBatis(注解SQL)<br>
前端：Bootstrap，HTML5+CSS3+JavaScript<br>
【其它】<br>
Java：1.8<br>
项目管理工具：Maven 3.6.3<br>
JavaWeb容器：Tomcat 8.5（推荐）

#### 使用说明

1.  使用IDEA 2017导入项目
2.  Maven -> Reimport（可能需要先在Settings修改Maven配置）
3.  IDEA中直接Run项目启动Tomcat
4.  浏览器访问http://localhost:8080/xyqas

#### 预览

主页<br>
![](/preview/主页.jpg)
管理员功能下拉列表<br>
![](/preview/管理员功能下拉列表.jpg)
发表贴子<br>
![](/preview/old/发表贴子_200227.jpg)
查看贴子<br>
![](/preview/查看贴子.jpg)

### 备注

1.  由于Project中使用了lombok，需要在IDEA安装IntelliJ Lombok plugin插件，否则编译时getter/setter等方法会报错<br>
2.  若Mavan Reimport失败，可考虑手动下载jar到本地仓库<br>
3.  物理机中是基于MySQL 5.7导出的SQL文件，因此建议安装MySQL 5.7以兼容我导出的SQL文件，目前已收到反馈：<br>
    （1）物理机MySQL 5.5导入我的SQL文件，发贴时无法由数据库生成时间，可能不兼容DEFAULT CURRENT_TIMESTAMP COMMENT。