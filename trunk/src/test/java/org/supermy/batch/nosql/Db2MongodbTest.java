package org.supermy.batch.nosql;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.mongodb.MongoDBItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.employee.domain.Employee;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:a-spring/a-spring.xml" })
public class Db2MongodbTest {

	@Value("${spring.batch.mongodb.name}")
	private String DB_NAME;

	@Value("${spring.batch.mongodb.collection.name}")
	private String COLLECTION_NAME;

	@Autowired
	private MyBatisPagingItemReader<Employee> reader;

	@Autowired
	private MyBatisBatchItemWriter<Employee> writer;

	@Autowired
	private MongoDBItemWriter mongodwriter;

	@Autowired
	private MongoClient mongod;

	@Autowired
	private SqlSession session;

	DBCollection collection;

	@Before
	public void setUp() throws Exception {
		// System.out.println(mongod);
		// System.out.println(DB_NAME);
		// System.out.println(COLLECTION_NAME);

		DB db = mongod.getDB(DB_NAME);
		// System.out.println(db);

		collection = db.getCollection(COLLECTION_NAME);
		// System.out.println(collection);

		collection = mongod.getDB(DB_NAME).createCollection(COLLECTION_NAME,
				null);
		// create an empty collection requires an insert and a remove
		collection.insert(new BasicDBObject());
		collection.remove(new BasicDBObject());

	}

	/**
	 * 手动job
	 * 
	 * @throws UnexpectedInputException
	 * @throws ParseException
	 * @throws Exception
	 */
	//@Test
	//@Transactional
	public void db2mongodb4customjob()
			throws UnexpectedInputException, ParseException, Exception {

		// mongodwriter.setTransactional(false);
		// mongodwriter.setConverter(new ObjectEmployeeConverter());

		assertEquals(10000, session.selectOne("check"));
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee = reader.read();
		while (employee != null) {
			System.out.println(">>>>>>>>>>>>>>>:" + employee.getId());
			System.out.println(">>>>>>>>>>>>>>>:" + employee.getSalary());
			employee.setSalary(employee.getSalary() * 2);
			employees.add(employee);
			employee = reader.read();
		}

		mongodwriter.write(employees);

		assertEquals(employees.size(), collection.count());

	}

	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job job;

	@Autowired
	JobParametersBuilder paramBuilder;

	/**
	 * 配置job参数， 调用
	 * 
	 * @throws IOException
	 */
	@Test
	public void db2mongodb4job() throws IOException {
		System.out.println("launcher:" + launcher);
		System.out.println("job:" + job);

		// given
		paramBuilder.addString("db", DB_NAME);
		paramBuilder.addString("collection", COLLECTION_NAME);
		paramBuilder.addString("transactional", "false");
		// paramBuilder.addString("itemConvert", "converter");
		paramBuilder.addDate("date", new Date());


		try {
			// when ...
			JobExecution execution = launcher.run(job,
					paramBuilder.toJobParameters());

			// then ...
			assertThat(execution.getExitStatus(), is(ExitStatus.COMPLETED));
			// assertCollectionCount(5);
			assertEquals(4, collection.count());

		} catch (JobExecutionException e) {
			e.printStackTrace();
			fail("Job execution failed");
		}
	}

}