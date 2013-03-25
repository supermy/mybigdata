package org.supermy.batch.esb;

import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动定时调度
 * spring mvc 启动时即调用
 * @author jamesmo
 *
 */
public class SpringEsbFileTest {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>begin");
		
		new ClassPathXmlApplicationContext("integration/spring-esb-file.xml");

		System.out.println(">>>>>>>>>>>>>>>>>>>>>end");
	}
}
