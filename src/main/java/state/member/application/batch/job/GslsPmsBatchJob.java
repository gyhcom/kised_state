package state.member.application.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GslsPmsBatchJob {
    private final JobLauncher jobLauncher;

    @Qualifier("gslsPmsAllStatDailyJob")
    private final Job gslsPmsAllStatDailyJob;

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runGslsPmsAllStatDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("gslsPmsAllStatDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(gslsPmsAllStatDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("국고보조금(PMS) runGslsPmsAllStatDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }
}
