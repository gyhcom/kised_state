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
import state.member.application.fasade.CertManager;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CertApiBatchConfig {
    private final CertManager certManager;


    // ==================== 창업기업확인 시스템 일일 단건 데이터 적재 시작 ============================
    @Bean
    public Job certCntJob(JobRepository jobRepository, Step certCntStep) {
        return new JobBuilder("certCntJob", jobRepository)
                .start(certCntStep)
                .build();
    }

    @Bean
    public Step certCntStep(JobRepository jobRepository, Tasklet certCntTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("certCntStep", jobRepository)
                .tasklet(certCntTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet certCntTasklet() {
        return ((contribution, chunkContext) -> {
            Map<String, Object> map = certManager.getCertCnt().block();

            // map에 대한 validation 체크
            if( map == null || map.isEmpty() ) {
                // 에러 테이블 적재 로직 추가

                throw new ApiException(ErrorCode.NULL_POINT, "창업기업확인 CERT CNT DATA IS NULL");
            }

            // 문제 없다면 적재 프로세스
            certManager.saveCertCnt(certManager.mapToEntity(map));

            //System.out.println("TEST BATCH : " + certManager.getCertCnt().block());
            return RepeatStatus.FINISHED;
        });
    }

    // ==================== 창업기업확인 시스템 일일 단건 데이터 적재 종료 ============================
}
