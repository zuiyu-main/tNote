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
