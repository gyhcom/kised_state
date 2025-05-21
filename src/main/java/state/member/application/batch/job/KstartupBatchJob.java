package state.member.application.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KstartupBatchJob {
    private final JobLauncher jobLauncher;

    @Qualifier("kstupDailyCntJob")
    private final Job kstupDailyCntJob;

    @Qualifier("kstupDailyListJob")
    private final Job kstupDailyListJob;

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runDailyCntBatchJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(kstupDailyCntJob, jobParameters);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runKstupDailyListJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution e = jobLauncher.run(kstupDailyListJob, jobParameters);
    }
}
