package org.supermy.employee.mapper;

import java.util.List;
import java.util.Map;

import org.supermy.employee.domain.Employee;
import org.supermy.mybatis.plugin.Pages;

/**
 * Mybatis映射接口文件
 * @author james mo
 *
 */
public interface EmployeeMapper {

	List<Employee> getEmployee();
	Integer updateEmployee(Employee employee);
	Integer check();

	List<Employee> findByPage(Pages page);
	
	List<Employee> findByPage1(Map<String,Object> page);

}
