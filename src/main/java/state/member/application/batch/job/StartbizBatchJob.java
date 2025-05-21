package state.member.application.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartbizBatchJob {
    private final JobLauncher jobLauncher;

    private final Job startbizDailyCntJob;

    //@Scheduled(cron = "*/10 * * * * ?")
    public void runStartbizDailyCntJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(startbizDailyCntJob, jobParameters);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
