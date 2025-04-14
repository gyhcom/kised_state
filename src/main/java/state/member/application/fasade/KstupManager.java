package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.kstartup.*;

import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class KstupManager {
    private final KstartupDurationProcessor kstartupDurationProcessor;
    private final KstartupPopKeywordProcessor kstartupPopKeywordProcessor;
    private final KstartupLoginCntProcessor kstartupLoginCntProcessor;
    private final KstartupMemberCntProcessor kstartupMemberCntProcessor;
    private final KstartupFieldsProcessor kstartupFieldsProcessor;
    private final KstartupCombNoticeProcessor kstartupCombNoticeProcessor;

    private final KstartupLginCntProcessor kstartupLginCntProcessor;
    private final KstartupBizPbancRegInstCntProcessor kstartupBizPbancRegInstCntProcessor;
    private final KstartupGetSearchStatusProcessor kstartupGetSearchStatusProcessor;
    private final KstartupInstBizPbancRegCntProcessor kstartupInstBizPbancRegCntProcessor;
    private final KstartupIntgPbancRegCntProcessor kstartupIntgPbancRegCntProcessor;
    private final KstartupMnpwCntProcessor kstartupMnpwCntProcessor;

    public Mono<Map<String, Object>> getCurrentMemberCnt() {
        return kstartupMemberCntProcessor.currentExecute();
    }

    public Mono<Map<String, Object>> getAnnualMemberCnt(String year) {
        return kstartupMemberCntProcessor.annualExecute(year);
    }

    public Mono<Map<String, Object>> getMonthlyMemberCnt(String year, String month) {
        return kstartupMemberCntProcessor.monthlyExecute(year, month);
    }

    public Flux<Map<String, Object>> getDailyLoginCnt(String year, String month) {
        return kstartupLoginCntProcessor.execute(year, month);
    }

    public Flux<Map<String, Object>> getAnnualPopKeyword(String year) {
        return kstartupPopKeywordProcessor.annualExecute(year);
    }

    public Flux<Map<String, Object>> getMonthlyPopKeyword(String year, String month) {
        return kstartupPopKeywordProcessor.monthlyExecute(year, month);
    }

    public Flux<Map<String, Object>> getWeeklyPopKeyword(String year, String month) {
        return kstartupPopKeywordProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getAnnualData() {
        return kstartupDurationProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getMonthlyData(String year) {
        return kstartupDurationProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getWeeklyData(String year, String month) {
        return kstartupDurationProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getFields() {
        return kstartupFieldsProcessor.execute();
    }

    public Flux<Map<String, Object>> getCombNotiReg(Map<String, Object> searchData) {
        return kstartupCombNoticeProcessor.execute(searchData);
    }

    /**
     * API 제공 후 사용
     */
    // 로그인 수 조회
    public Mono<Map<String, Object>> lginCnt() {
        return kstartupLginCntProcessor.execute();
    }

    // 통합공고 등록 건수 조회
    public Mono<Map<String, Object>> intgPbancRegCnt() {
        return kstartupIntgPbancRegCntProcessor.execute();
    }

    // 사업공고 등록 기관(주관기관) 수 조회
    public Mono<Map<String, Object>> bizPbancRegInstCnt() {
        return kstartupBizPbancRegInstCntProcessor.execute();
    }

    // 기관유형별 사업공고 등록 건수 조회
    public Flux<Map<String, Object>> instBizPbancRegCnt() {
        return kstartupInstBizPbancRegCntProcessor.execute();
    }

    // 최근 7일 인기 검색어 목록
    public Flux<Map<String, Object>> getSearchStatus(String weekDaySe) {
        return kstartupGetSearchStatusProcessor.execute(weekDaySe);
    }

    // 회원 수 조회
    public Mono<Map<String, Object>> mnpwCnt() {
        return kstartupMnpwCntProcessor.execute();
    }
}
