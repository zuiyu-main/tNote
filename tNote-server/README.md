# 环境搭建
# mysql  
数据库名： mynote
账号： root
密码： root
端口： 3306
IP： localhost
初始化sql：见note.sql文件
# redis
ip： localhost
端口：6379
密码： redis ，redis没有配置密码此处可以去掉
# mongodb
ip： localhost
端口： 27017
库名： tNote
# 启动
环境连接好，启动MyNoteApplication即可
# swagger文档访问
http://localhost:2000/swagger-ui.html
#generator生成代码
1. 打开本目录下resources/generator/generatorConfig.xml文件
2. 修改绝对地址，targetProject为生成的代码位置，targetPackage为目标包名
`

        <javaModelGenerator targetPackage="com.tz.entity.bean"
                            targetProject="/cxt/codework/mywork/tNote/tNote-server/tNote-entity/src/main/java"/>

        <sqlMapGenerator targetPackage="mapper"
                         targetProject="/cxt/codework/mywork/tNote/tNote-server/tNote-dao/src/main/resources"/>

        <javaClientGenerator targetPackage="com.tz.dao.dao"
                             targetProject="/cxt/codework/mywork/tNote/tNote-server/tNote-dao/src/main/java"
                             type="XMLMAPPER"/>
`
3. 修改需要创建的表名
![image](https://github.com/TianPuJun/tNote/blob/more-module/tNote-server/tNote-generator/src/img/WX20190910-094829%402x.png)
<table tableName="note_log"><generatedKey column="id" sqlStatement="JDBC"/></table>
4. 运行maven插件

![image](https://github.com/TianPuJun/tNote/blob/more-module/tNote-server/tNote-generator/src/img/WX20190910-095137%402x.png)

5. docker 部署
第一步：先package 打包生成jar文件
第二步：配置docker插件配置，运行生成镜像和容器
配置截图
![image](https://github.com/TianPuJun/tNote/blob/master/tNote-server/src/main/resources/img/docker_run_setter.png)
