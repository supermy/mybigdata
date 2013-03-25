package org.supermy.batch.jdbc;

import org.springframework.batch.item.ItemProcessor;
import org.supermy.employee.domain.Employee;

public class JdbcProcessor implements ItemProcessor<Employee, Employee> {

    /**
     * XML文件内容处理。
     * 
     */
    public Employee process(Employee e) throws Exception {
    	System.out.println(">>>>>>>>>>>>>>process:"+e);
    	return e;
    }
}