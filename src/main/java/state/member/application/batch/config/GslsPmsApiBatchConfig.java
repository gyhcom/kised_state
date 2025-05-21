package state.member.application.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.fasade.GslsEsbManager;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GslsPmsApiBatchConfig {
    private final GslsEsbManager gslsEsbManager;

    // ===================== 국고보조금(PMS) 일일 단건 데이터 적재 시작 ====================
    @Bean
    public Job gslsPmsAllStatDailyJob(JobRepository jobRepository, Step gslsPmsAllStatDailyStep) {
        return new JobBuilder("gslsPmsAllStatDailyJob", jobRepository)
                .start(gslsPmsAllStatDailyStep)
                .build();
    }

    @Bean
    public Step gslsPmsAllStatDailyStep(JobRepository jobRepository, Tasklet gslsPmsAllStatDailyTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("gslsPmsAllStatDailyStep", jobRepository)
                .tasklet(gslsPmsAllStatDailyTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet gslsPmsAllStatDailyTasklet() {
        return ((contribution, chunkContext) -> {

            Map<String, Object> map = gslsEsbManager.getAllStatCnt().block();

            //map에 대한 validation 체크
            if( map == null || map.isEmpty() ) {
                throw new ApiException(ErrorCode.NULL_POINT, "국고보조금(PMS) getAllStatCnt ERROR");
            }

            // 문제 없다면 적재 프로세스 진행
            gslsEsbManager.saveDailyStat(gslsEsbManager.mapToEntity(map));

            return RepeatStatus.FINISHED;
        });
    }
    // ===================== 국고보조금(PMS) 일일 단건 데이터 적재 적재 종료 ====================
}
