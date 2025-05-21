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
public class FdsBatchJob {
    private final JobLauncher jobLauncher;

    @Qualifier("fdsTotDetCntDailyJob")
    private final Job fdsTotDetCntDailyJob;

    @Qualifier("fdsDetCntByTypeDailyJob")
    private final Job fdsDetCntByTypeDailyJob;

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runFdsTotCndDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("fdsTotDetCntDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(fdsTotDetCntDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("사업비점검 runFdsTotDetCntDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    //@Scheduled(cron = "*/10 * * * * ?") // 10초 마다
    public void runFdsDetCntByTypeDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("runFdsDetCntByTypeDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(fdsDetCntByTypeDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("사업비점검 runFdsDetCntByTypeDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }
}
