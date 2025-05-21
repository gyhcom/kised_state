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
public class CertBatchJob {
    private final JobLauncher jobLauncher;

    private final Job certCntJob;

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runCertCntBatchJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(certCntJob, jobParameters);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
