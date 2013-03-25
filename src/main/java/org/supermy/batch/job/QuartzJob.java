package org.supermy.batch.job;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 定时调度类
 *
 */

@Component("myQuartzJob")
public class QuartzJob {

	private static final Logger LOG = LoggerFactory.getLogger(QuartzJob.class);

	private static long counter = 0l;
	
	@Value("${spring.batch.mongodb.name}")
	private String DB_NAME;

	@Value("${spring.batch.mongodb.collection.name}")
	private String COLLECTION_NAME;
	
	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job job;
	
	@Autowired
	JobParametersBuilder paramBuilder;

	public void execute() throws IOException {
		LOG.debug("start...");
		System.out.println("start...");
		StopWatch sw = new StopWatch();
		sw.start();
		
		System.out.println("launcher:" + launcher);
		System.out.println("job:" + job);

		// given
		paramBuilder.addString("db", DB_NAME);
		paramBuilder.addString("collection", COLLECTION_NAME);
		paramBuilder.addString("transactional", "false");
		//paramBuilder.addString("itemConvert", "converter");
		/*
		 * Spring Batch Job同一个job instance，成功执行后是不允许重新执行的【失败后是否允许重跑，
		 * 可通过配置Job的restartable参数来控制，默认是true】，如果需要重新执行，可以变通处理，
		 * 添加一个JobParameters构建类,以当前时间作为参数，保证其他参数相同的情况下却是不同的job instance
		 */
		paramBuilder.addDate("date", new Date());

		try {
			// when ...
			JobExecution execution = launcher.run(job,
					paramBuilder.toJobParameters());

			// then ...
			assertThat(execution.getExitStatus(), is(ExitStatus.COMPLETED));
			// assertCollectionCount(5);
			//assertEquals(4, collection.count());

		} catch (JobExecutionException e) {
			e.printStackTrace();
			fail("Job execution failed");
		}
		
		
		sw.stop();
		LOG.debug("Time elapsed:{},Execute quartz ledgerJob:{}", sw.prettyPrint(), ++counter);
		
	}
	
}
