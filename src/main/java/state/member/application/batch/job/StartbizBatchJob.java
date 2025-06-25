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
            log.error("법인설립시스템 startbizDailyCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }
}
