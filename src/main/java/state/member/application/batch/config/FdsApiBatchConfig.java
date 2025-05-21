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
import state.member.application.fasade.FdsManager;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FdsApiBatchConfig {
    private final FdsManager fdsManager;

    // ===================== 사업비점검 이상거래탐지 총 이상거래 탐지 건수 데이터 적재 시작 ====================
    @Bean
    public Job fdsTotDetCntDailyJob(JobRepository jobRepository, Step fdsTotDetCntDailyStep) {
        return new JobBuilder("fdsTotDetCntDailyJob", jobRepository)
                .start(fdsTotDetCntDailyStep)
                .build();
    }

    @Bean
    public Step fdsTotDetCntDailyStep(JobRepository jobRepository, Tasklet fdsTotDetCntDailyTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fdsTotDetCntDailyStep", jobRepository)
                .tasklet(fdsTotDetCntDailyTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet fdsTotDetCntDailyTasklet() {
        return ((contribution, chunkContext) -> {

            Map<String, Object> map = fdsManager.getTotDetCnt().block();

            //map에 대한 validation 체크
            if( map == null || map.isEmpty() ) {
                throw new ApiException(ErrorCode.NULL_POINT, "사업비점검 이상거래탐지 getTotDetCnt NULL");
            }

            // 문제 없다면 적재 프로세스 진행
            fdsManager.saveTotCnt(fdsManager.mapToEntity(map));

            return RepeatStatus.FINISHED;
        });
    }
    // ===================== 사업비점검 이상거래탐지 총 이상거래 탐지 건수 데이터 적재 종료 ====================

    // ===================== 사업비점검 이상거래탐지 유형별 탐지 건수 데이터 적재 시작 ====================
    @Bean
    public Job fdsDetCntByTypeDailyJob(JobRepository jobRepository, Step fdsDetCntByTypeDailyStep) {
        return new JobBuilder("fdsDetCntByTypeDailyJob", jobRepository)
                .start(fdsDetCntByTypeDailyStep)
                .build();
    }

    @Bean
    public Step fdsDetCntByTypeDailyStep(JobRepository jobRepository, Tasklet fdsDetCntByTypeDailyTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fdsDetCntByTypeDailyStep", jobRepository)
                .tasklet(fdsDetCntByTypeDailyTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet fdsDetCntByTypeDailyTasklet() {
        return (((contribution, chunkContext) -> {
            Map<String, Object> map = fdsManager.getDetCntByType().block();

            if(map == null || map.isEmpty()) {
                throw new ApiException(ErrorCode.NULL_POINT, "사업비점검 이상거래탐지 getDetCntByType ERROR");
            }

            // 문제 없다면 저장 로직 실행
            fdsManager.saveDetCntByType(fdsManager.listToEntity(map));

            return RepeatStatus.FINISHED;
        }));
    }
    // ===================== 사업비점검 이상거래탐지 유형별 탐지 건수 데이터 적재 종료 ====================
}
