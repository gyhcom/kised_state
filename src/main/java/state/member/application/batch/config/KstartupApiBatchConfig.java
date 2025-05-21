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
import state.member.application.fasade.KstupManager;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.entity.KstupInstPbancRegStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KstartupApiBatchConfig {
    private final KstupManager kstupManager;

    // ==================== K-STARTUP 일일 단건 데이터 적재 시작 ============================
    @Bean
    public Job kstupDailyCntJob(JobRepository jobRepository,
                                Step lginCntStep,
                                Step intgPbancRegCntStep,
                                Step bizPbancRegInstCntStep) {
        return new JobBuilder("kstupDailyCntJob", jobRepository)
                .start(lginCntStep)     //로그인 수
                .next(intgPbancRegCntStep)  //통합공고 등록 건수
                .next(bizPbancRegInstCntStep) // 사업공고 등록 기관 수
                .build();
    }
    @Bean
    public Step lginCntStep(JobRepository jobRepository, Tasklet lginCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("lginCntStep", jobRepository)
                .tasklet(lginCntTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet lginCntTasklet (){
        return ((contribution, chunkContext) -> {
            // 로그인 수 조회
            Map<String, Object> lginMap = kstupManager.lginCnt().block();
            // 통합공고 등록 건수 조회
            Map<String, Object> intgPbancRegMap = kstupManager.intgPbancRegCnt().block();
            // 사업공고 등록 기관 수 조회
            Map<String, Object> bizPbancRegInstMap = kstupManager.bizPbancRegInstCnt().block();
            // 회원 수 조회
            Map<String, Object> mnpwMap = kstupManager.mnpwCnt().block();

            /**
             * 로그인 수, 회원 수 등 한번에 등록되는 방법이기 때문에 Null 체크를 한번에 진행
             * 이 방법이 수정되어야 한다면 merge into를 사용하여 등록하는 방법을 고려해보기
             */
            if( lginMap == null || lginMap.isEmpty() || lginMap.getOrDefault("resultData", "N").equals("N") ) {
                throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 로그인 수 조회 실패");
            } else if( intgPbancRegMap == null || intgPbancRegMap.isEmpty() || intgPbancRegMap.getOrDefault("resultData", "N").equals("N") ) {
                throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 통합공고 등록 건수 조회 실패");
            } else if( bizPbancRegInstMap == null || bizPbancRegInstMap.isEmpty() || bizPbancRegInstMap.getOrDefault("resultData", "N").equals("N") ) {
                throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 사업공고 등록 기관 수 조회 실패");
            } else if( mnpwMap == null || mnpwMap.isEmpty() || mnpwMap.getOrDefault("resultData", "N").equals("N") ) {
                throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 회원 수 조회 실패");
            }

            //mapToEntity에 보낼 파라미터 map 생성
            Map<String, Object> map = new HashMap<>();
            map.put("lginCnt", lginMap.get("resultData"));
            map.put("intgPbancRegCnt", intgPbancRegMap.get("resultData"));
            map.put("bizPbancRegInstCnt", bizPbancRegInstMap.get("resultData"));
            map.put("mnpwCnt", mnpwMap.get("resultData"));

            // 문제 없다면 저장 로직 실행
            kstupManager.saveDailyCnt(kstupManager.mapToEntity(map));

            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step intgPbancRegCntStep(JobRepository jobRepository, Tasklet intgPbancRegCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("intgPbancRegCntStep", jobRepository)
                .tasklet(intgPbancRegCntTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet intgPbancRegCntTasklet (){
        return ((contribution, chunkContext) -> {
            Map<String, Object> map = kstupManager.intgPbancRegCnt().block();

            if( map == null || map.isEmpty() ) {
                log.error("K-Startup intgPbancRegCnt ERROR");
                // 에러 테이블 적재 로직 추가

                throw new ApiException(ErrorCode.NULL_POINT);
            }

            // 문제 없다면 저장 로직 실행 -> merge into 가능한지 확인
            System.out.println("통합공고 등록 건수 BATCH : " + map);

            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step bizPbancRegInstCntStep(JobRepository jobRepository, Tasklet bizPbancRegInstCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("bizPbancRegInstCntStep", jobRepository)
                .tasklet(bizPbancRegInstCntTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet bizPbancRegInstCntTasklet (){
        return ((contribution, chunkContext) -> {
            Map<String, Object> map = kstupManager.intgPbancRegCnt().block();

            if( map == null || map.isEmpty() ) {
                log.error("K-Startup bizPbancRegInstCntTasklet ERROR");
                // 에러 테이블 적재 로직 추가

                throw new ApiException(ErrorCode.NULL_POINT);
            }

            // 문제 없다면 저장 로직 실행 -> merge into 가능한지 확인
            System.out.println("사업공고 등록 기관 수 BATCH : " + map);

            return RepeatStatus.FINISHED;
        });
    }
    // ==================== K-STARTUP 일일 단건 데이터 적재 종료 ============================

    // ==================== K-STARTUP 일일 리스트 데이터 적재 시작 ============================
    @Bean
    public Job kstupDailyListJob(JobRepository jobRepository,
                                Step instBizPbancRegCntStep) {
        return new JobBuilder("kstupDailyListJob", jobRepository)
                .start(instBizPbancRegCntStep)
                .build();
    }

    @Bean
    public Step instBizPbancRegCntStep(JobRepository jobRepository, Tasklet instBizPbancRegCntTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("instBizPbancRegCntStep", jobRepository)
                .tasklet(instBizPbancRegCntTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet instBizPbancRegCntTasklet() {
        return ((contribution, chunkContext) -> {
            Map<String, Object> dataMap = kstupManager.instBizPbancRegCnt().block();

            if( dataMap == null || dataMap.isEmpty() ) {
                // 에러 테이블 적재 로직 추가

                throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 기관유형별 사업공고 등록 건수 API 응답 데이터가 비어있습니다.");
            }

            // 문제 없다면 저장 로직 실행
            kstupManager.saveBizPbancRegCntList(kstupManager.listToEntity(dataMap));

            return RepeatStatus.FINISHED;
        });
    }
    // ==================== K-STARTUP 일일 리스트 데이터 적재 종료 ============================
}
