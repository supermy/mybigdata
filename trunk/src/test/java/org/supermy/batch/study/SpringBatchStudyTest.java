package org.supermy.batch.study;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchStudyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"batchstudy/spring-batch-study.xml");
		
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("helloWorldJob");
		Job csv = (Job) context.getBean("csvJob");
		Job xml = (Job) context.getBean("xmlFileReadAndWriterJob");

		Job fixed = (Job) context.getBean("fixedLengthJob");

		Job multi = (Job) context.getBean("multiTypeSingleFileJob");


		try {
			/* 运行Job */
			JobExecution result = launcher.run(job, new JobParameters());
			/* 处理结束，控制台打印处理结果 */
			System.out.println(result.toString());

			/* 运行Job */
			JobExecution resultcsv = launcher.run(csv, new JobParameters());
			/* 处理结束，控制台打印处理结果 */
			System.out.println(resultcsv.toString());


			// JOB实行 I:/html5examples/mobile-cloudmanager/B.mobile-rest/src/test/resources
			JobExecution resultxml = launcher.run(xml, new JobParametersBuilder()
			.addString("inputFilePath",  "batchstudy/in/spring-batch-input.xml")
			.addString("outputFilePath", "batchstudy/out/spring-batch-output.xml")
			.toJobParameters());
			// 运行结果输出
			System.out.println(resultxml.toString());


			// JOB实行
			JobExecution resultfixed = launcher.run(
					fixed,
					new JobParametersBuilder()
					.addString("inputFilePath",
							"batchstudy/in/fixedLengthInputFile.txt")
							.addString("outputFilePath",
									"batchstudy/out/fixedLengthOutputFile.txt")
									.toJobParameters());
			// 运行结果输出
			System.out.println(resultfixed.toString());     


			//            // JOB实行  FIXME
			//            JobExecution resultmulti = launcher.run(
			//                    multi,
			//                    new JobParametersBuilder()
			//                            .addString("inputFilePath",
			//                                    "multiTypesInput.txt")
			//                            .addString("outputFilePathStudent",
			//                                    "student.txt")
			//                            .addString("outputFilePathGoods",
			//                                    "goods.csv")
			//                            .toJobParameters());
			//            // 运行结果输出
			//            System.out.println(resultmulti.toString());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}