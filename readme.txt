项目说明
	1.项目采用struts2 spring 和 mybatis 搭建而成，扩展了mybatis的插件，使得可以自动生成分页支持以及生成单表的CRUD操作
	2.项目com.eatle.persistent.generator 包里包含了核心的插件代码 利用这些插件代码
	3.项目目录：
			action--->com.eatle.action 包里，对应的strtus配置在 res.struts里
			service-->com.eatle.service包里
			dao------>com.eatle.persistent.mapper 包里
			
			
运行项目
           本着开源精神 数据库采用了mysql.
     1.首先创建数据库:eatledb 
     2.运行res.sql里de Sql文件
     3.部署项目 启动 ，访问：http://localhost:8080/eatle

远景：打造一个简单的开源软件，让初学者了解开源软件以及传播开源精神


注意：本软件采用的是eclipse for java ee版本，所以 如果你用的myeclipse的话    请注意：WebRoot 改成 WebContent


     
 