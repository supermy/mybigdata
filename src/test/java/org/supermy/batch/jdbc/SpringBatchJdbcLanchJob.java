package org.supermy.batch.jdbc;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 定时调度类
 *
 */
public class SpringBatchJdbcLanchJob {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBatchJdbcLanchJob.class);
	
	
	 /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "integration/spring-batch-jdbc.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("jdbcJob");

        try {
            /* 运行Job */
            
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("id",  "1");
			jobParametersBuilder.addString("time", new Date().getTime()+"");
			JobExecution result = launcher.run(job, jobParametersBuilder.toJobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
