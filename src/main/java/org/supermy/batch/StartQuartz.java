package org.supermy.batch;

import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动定时调度
 * spring mvc 启动时即调用
 * @author jamesmo
 *
 */
public class StartQuartz {

	public static void main(String[] args) throws FileNotFoundException {

		new ClassPathXmlApplicationContext("classpath:a_spring/c7-db2mongodb-batch.xml");
	}
}
