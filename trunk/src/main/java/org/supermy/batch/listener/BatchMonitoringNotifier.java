package org.supermy.batch.listener;

import org.springframework.batch.core.JobExecution;

public interface BatchMonitoringNotifier {

    void notify(JobExecution jobExecution);

}