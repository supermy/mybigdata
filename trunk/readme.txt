//专注于数据的抽取和加工

mvn clean         		--> cleans the project
mvn clean test    		--> cleans the project and runs all tests
mvn clean package 		--> cleans the project and builds the WAR
mvn tomcat:run	 		--> web run
mvn eclipse:eclipse		--> gen eclipse .project and .classpath config file
mvn jar:jar				--> only build jar file.
mvn javadoc:javadoc     --> gen api files;

mvn dependency:copy-dependencies  拷贝jar
mvn dependency:tree

mvn test -Dtest={测试文件名称}

support:
	mybatis
	data auto init
	service junit test
	junit test

默认规范	
	service:包的默认规范  **.service
	mybatis mapper:包的默认规范  **.mapper
	sqlSessionFactory.configLocation: 默认位置 classpath*:spring/mybatis/mybatis-config.xml

!!!*------------------------------------------------------------------------------------*!!!	
2013-01-28
	增加文件自动探测，启动job任务。
	
2013-01-14
	read&write->手动job->配置job->定时调度job

	专门版本抽取。spring-mybatis 测试完成
	mini spring  batch todo ...	
	先完成简单的读取和写入，配置job;  OK
	完成定时调度。todo 数据初始化。
	
2013-01-13
	db2mongodb:mybatis(hsql) to mongodb,测试完成之后，转换为oracle是很简单的事情；
		mybatis2mybatis mongodb2mongodb是默认可行;
		都可以使用domain进行对象的读取和写入，mongodb需要一个ObjectConvert；
		
		ok 以上代码已经测试通过。
		
		先完成简单的读取和写入，在配置job;
		最后可以在配置表里面完成数据转换的配置。
		加入规则引擎对数据的处理。
		
		
	通过内置的hsql编写测试代码，进行参数初始化配置。最后在生产机的时候再转化为生产机所用的数据库。
		

2012-12-13
mybatis-spring mybatis-batch sample
spring-batch-samples jdbc-batch sample

2012-12-11
spring batch for oracle2mongodb

2012-12-09
spring batch 