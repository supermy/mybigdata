package org.supermy.batch.esb;


import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.util.Assert;

/**
 * @author jamesmo
 *
 */
@MessageEndpoint
public class JDBCToJobLaunchRequestAdapter implements InitializingBean {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(job, "A Job must be provided");
    }
    
    @ServiceActivator
    //此处如要丢东西到下一CHANNEL，则要设返回对象
    public JobLaunchRequest handleJdbcMessage(List<Map<String, Object>> message) {
        for (Map<String, Object> resultMap : message) {
            System.out.println("Row");
            for (String column : resultMap.keySet()) {
                System.out.println("column: " + column + " value: "
                        + resultMap.get(column));
            }
        }
        
        JobParameters jobParameters = new JobParametersBuilder().
                addString("id", "1").
                addLong("time.stamp", System.currentTimeMillis()).
                toJobParameters();

        if (job.getJobParametersIncrementer() != null) {
            jobParameters = job.getJobParametersIncrementer().getNext(jobParameters);
        }

        return new JobLaunchRequest(job, jobParameters);
    }

}