package org.supermy.batch.esb;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringEsb4JdbcTest {
	
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("integration/spring-esb-jdbc.xml");
        context.start(); //让线程在这里阻塞，防止JVM退出
    }
}
