package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.kstartup.*;

import java.util.Map;

@Transactional
@Service
public class KstupManager {
    private final KstartupDurationProcessor kstartupDurationProcessor;
    private final KstartupPopKeywordProcessor kstartupPopKeywordProcessor;
    private final KstartupLoginCntProcessor kstartupLoginCntProcessor;
    private final KstartupMemberCntProcessor kstartupMemberCntProcessor;
    private final KstartupFieldsProcessor kstartupFieldsProcessor;
    private final KstartupCombNoticeProcessor kstartupCombNoticeProcessor;

    public KstupManager(
            KstartupDurationProcessor kstartupDurationProcessor,
            KstartupPopKeywordProcessor kstartupPopKeywordProcessor,
            KstartupLoginCntProcessor kstartupLoginCntProcessor,
            KstartupMemberCntProcessor kstartupMemberCntProcessor,
            KstartupFieldsProcessor kstartupFieldsProcessor,
            KstartupCombNoticeProcessor kstartupCombNoticeProcessor) {
        this.kstartupDurationProcessor = kstartupDurationProcessor;
        this.kstartupPopKeywordProcessor = kstartupPopKeywordProcessor;
        this.kstartupLoginCntProcessor = kstartupLoginCntProcessor;
        this.kstartupMemberCntProcessor = kstartupMemberCntProcessor;
        this.kstartupFieldsProcessor = kstartupFieldsProcessor;
        this.kstartupCombNoticeProcessor = kstartupCombNoticeProcessor;
    }

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
}
