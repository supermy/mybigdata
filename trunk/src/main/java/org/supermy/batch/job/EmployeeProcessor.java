package org.supermy.batch.job;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.supermy.employee.domain.Employee;

/**
 * 业务处理类。
 * 
 */
@Component("employeeProcessor")
public class EmployeeProcessor implements
        ItemProcessor<Employee, Employee> {

    /**
     * 对取到的数据进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    public Employee process(Employee empl) throws Exception {
    	System.out.println("process >>>>>>>>>>>>:"+empl.getName());
    	return empl;
    }

}