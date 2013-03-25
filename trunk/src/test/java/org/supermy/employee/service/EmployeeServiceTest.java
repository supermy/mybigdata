package org.supermy.employee.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.supermy.employee.domain.Employee;
import org.supermy.mybatis.plugin.Pages;

/**
 * 测试spring-mybatis
 * 
 * @author jamesmo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:a_spring/a-spring.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class EmployeeServiceTest {

	private final Logger logger = LoggerFactory
			.getLogger(EmployeeServiceTest.class);

	@Autowired
	private EmployeeService cs;

	private Pages page = new Pages(10, 3);

	@Test
	public void queryPage() {

		Map<String, Object> page1 = new HashMap<String, Object>();
		page1.put("page", new Pages(1, 2));
		List<Employee> result1 = cs.findByPage(page1);
		logger.debug("result:{}", result1);
		logger.debug("result:{}", page);
		//String a="".;

	}
}
