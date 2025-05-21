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
import state.member.application.fasade.KisedorkrManager;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KisedorkrApiBatchConfig {
    private final KisedorkrManager kisedorkrManager;

    // ==================== 기관 홈페이지 일일 단건 데이터 적재 시작 ============================
    @Bean
    public Job kisedorkrDailyCntJob(JobRepository jobRepository, Step kisedorkrDailyCntStep) {
        return new JobBuilder("kisedorkrDailyCntJob", jobRepository)
                .start(kisedorkrDailyCntStep)
                .build();
    }

    @Bean
    public Step kisedorkrDailyCntStep(JobRepository jobRepository, Tasklet kisedorkrDailyCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("kisedorkrDailyCntStep", jobRepository)
                .tasklet(kisedorkrDailyCntTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet kisedorkrDailyCntTasklet() {
        return ((contribution, chunkContext) -> {
            Map<String, Object> map = kisedorkrManager.visitCnt().block();

            if( map == null || map.isEmpty() ) {
                log.error("기관 홈페이지 visitCnt ERROR");
                // 에러 테이블 적재 로직 추가

                throw new ApiException(ErrorCode.NULL_POINT);
            }

            // 문제 없다면 저장 로직 실행
            kisedorkrManager.saveVisitCnt(kisedorkrManager.mapToEntity(map));

            return RepeatStatus.FINISHED;
        });
    }
    // ==================== 기관 홈페이지 일일 단건 데이터 적재 종료 ============================
}
