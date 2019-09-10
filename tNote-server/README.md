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
# 模块介绍
tNote-base 父模块，通用类，util，配置
tNote-dao dao层，连接数据库
tNote-entity 实体类
tNote-service 服务
tNote-web 访问层
tNote-generator generator生成代码