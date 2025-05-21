package state.member.application.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KisedorkrBatchJob {
    private final JobLauncher jobLauncher;

    private final Job kisedorkrDailyCntJob;

    //@Scheduled(cron = "*/5 * * * * ?")
    public void runKisedDailyCntJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(kisedorkrDailyCntJob, jobParameters);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
