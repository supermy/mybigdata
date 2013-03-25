package org.supermy.batch.listener;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.springframework.batch.core.JobExecution;


public class LogMonitoringNotifier implements BatchMonitoringNotifier {

    private String formatExceptionMessage(Throwable exception) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }

    private String createMessageContent(JobExecution jobExecution) {
        List<Throwable> exceptions = jobExecution.getAllFailureExceptions();
        StringBuilder content = new StringBuilder();
        content.append("Job execution #");
        content.append(jobExecution.getId());
        content.append(" of job instance #");
        content.append(jobExecution.getJobInstance().getId());
        content.append(" failed with following exceptions:");
        for (Throwable exception : exceptions) {
            content.append("");
            content.append(formatExceptionMessage(exception));
        }
        return content.toString();
    }

    public void notify(JobExecution jobExecution) {
        String content = createMessageContent(jobExecution);
        System.out.println(content);
    }
} 