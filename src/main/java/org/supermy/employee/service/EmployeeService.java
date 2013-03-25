package org.supermy.employee.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.employee.domain.Employee;
import org.supermy.employee.mapper.EmployeeMapper;
import org.supermy.mybatis.plugin.Pages;

/**
 * 
 * @author james mo
 * 
 */
@Repository
@Transactional(value = "transactionManager")
public class EmployeeService {
	private final Logger logger = LoggerFactory
			.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeMapper EmployeeMapper;


	@CacheEvict(value = "EmployeeCache", key = "#Employee.name + 'findByName'")
	public Employee updateEmployee(Employee Employee) {
		Integer updateEmployee = EmployeeMapper.updateEmployee(Employee);
		logger.debug("update Employee amount:{}", updateEmployee);
		logger.debug("update Employee id:{}", Employee.getId());

		return Employee;
	}

	@Cacheable(value = "EmployeeCache")
	public List<Employee> findAllPage(Pages page) {

		List<Employee> Employees = EmployeeMapper.findByPage(page);
		logger.debug("find Employees amount:{}", Employees.size());

		return Employees;
	}

	@Cacheable(value = "EmployeeCache")//, key = "#page.page.currentPage +#page.page.pageSize + 'page'"
	@ManagedOperation(currencyTimeLimit=10)
	//@ManagedAttribute
	public List<Employee> findByPage(Map<String, Object> page) {

		List<Employee> Employees = EmployeeMapper.findByPage1(page);
		logger.debug("find Employees amount:{}", Employees.size());

		return Employees;
	}

}
