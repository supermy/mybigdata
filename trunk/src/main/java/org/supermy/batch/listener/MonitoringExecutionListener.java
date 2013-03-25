package org.supermy.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class MonitoringExecutionListener {
    private BatchMonitoringNotifier monitoringNotifier;

    @BeforeJob
    public void executeBeforeJob(JobExecution jobExecution) {
    	System.out.println(">>>>>>>>>>>>>> execute before");
        // Do nothing
    }

    @AfterJob
    public void executeAfterJob(JobExecution jobExecution) {
    	System.out.println(">>>>>>>>>>>>>> execute after");
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // Notify when job fails
            monitoringNotifier.notify(jobExecution);
        }
    }

    public void setMonitoringNotifier(BatchMonitoringNotifier monitoringNotifier) {
        this.monitoringNotifier = monitoringNotifier;
    }
}
