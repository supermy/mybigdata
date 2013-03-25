package org.supermy.batch.esb;


import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author jamesmo
 *
 */
@MessageEndpoint
public class FileProcessor implements InitializingBean {

    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public void afterPropertiesSet() throws Exception {
       // Assert.notNull(job, "A Job must be provided");
    }

    @ServiceActivator
    public void adapt(File file) throws NoSuchJobException {
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>:"+file);
}

}