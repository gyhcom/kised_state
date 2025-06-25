package state.member.application.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
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
            log.error("기관 홈페이지 kisedorkrDailyCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }
}
