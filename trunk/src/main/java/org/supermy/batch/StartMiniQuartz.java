package org.supermy.batch;

import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动定时调度
 * spring mvc 启动时即调用
 * @author jamesmo
 *
 */
public class StartMiniQuartz {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>begin");
		new ClassPathXmlApplicationContext("a_spring/c6-mini-batch.xml");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>end");
	}
}
