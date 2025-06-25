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
import state.member.application.command.CommResponseCommand;
import state.member.application.fasade.StartbizManager;
import state.member.application.fasade.TabCommStatsManager;
import state.member.application.util.ResponseUtil;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StartbizApiBatchConfig {
    private final StartbizManager startbizManager;
    private final TabCommStatsManager commManager;

    // ==================== 법인설립시스템 일일 단건 데이터 적재 시작 ============================
    @Bean
    public Job startbizDailyCntJob(JobRepository jobRepository, Step startbizDailyCntStep) {
        return new JobBuilder("startbizDailyCntJob", jobRepository)
                .start(startbizDailyCntStep)     //로그인 수
                .build();
    }
    @Bean
    public Step startbizDailyCntStep(JobRepository jobRepository, Tasklet startbizDailyCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("startbizDailyCntStep", jobRepository)
                .tasklet(startbizDailyCntTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet startbizDailyCntTasklet (){
        return ((contribution, chunkContext) -> {
            Map<String, Object> map = startbizManager.bizStatsInfoApi().block();

            if( map == null || map.isEmpty() ) {
                throw new ApiException(ErrorCode.NULL_POINT, "법인설립시스템 bizStatsInfoApi 적재 Batch ERROR");
            }

            // 문제 없다면 저장 로직 실행
            startbizManager.saveDailyCnt(startbizManager.mapToEntity(map));

            return RepeatStatus.FINISHED;
        });
    }
    // ==================== 법인설립시스템 일일 단건 데이터 적재 종료 ============================

    // ==================== 법인설립시스템 연, 월, 일 데이터 적재 시작 ============================
    @Bean
    public Job startbizStatsByDateJob(JobRepository jobRepository, Step startbizStatsByDateStep) {
        return new JobBuilder("startbizStatsByDateJob", jobRepository)
                .start(startbizStatsByDateStep)     //로그인 수
                .build();
    }
    @Bean
    public Step startbizStatsByDateStep(JobRepository jobRepository, Tasklet startbizStatsByDateTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("startbizStatsByDateStep", jobRepository)
                .tasklet(startbizStatsByDateTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet startbizStatsByDateTasklet (){
        return ((contribution, chunkContext) -> {
            CommResponseCommand comm = startbizManager.getStartbizStatsCnt().block();

            if( comm == null || comm.isEmpty() ) {
                throw new ApiException(ErrorCode.NULL_POINT, "법인설립시스템 getStartbizStatsCnt IS ERROR");
            }

            // 문제 없다면 저장 로직 실행
            commManager.saveAllStats(ResponseUtil.listToEntityNewForm(comm));

            return RepeatStatus.FINISHED;
        });
    }
    // ==================== 법인설립시스템 연, 월, 일 데이터 적재 종료 ============================
}
