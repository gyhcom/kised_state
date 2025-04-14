package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.kisedorkr.KisedorkrDurationProcessor;
import state.member.application.processor.kisedorkr.KisedorkrVisitCntProcessor;
import state.member.application.processor.kstartup.KstartupBizPbancRegInstCntProcessor;

import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class KisedorkrManager {
    private final KisedorkrDurationProcessor kisedorkrDurationProcessor;
    private final KisedorkrVisitCntProcessor kisedorkrVisitCntProcessor;

    public Flux<Map<String, Object>> getAnnulData() {
        return kisedorkrDurationProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getMonthlyData(String year) {
        return kisedorkrDurationProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getWeeklyData(String year, String month) {
        return kisedorkrDurationProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getGridData() {
        return kisedorkrDurationProcessor.getGridData();
    }

    //로그인 수 조회
    public Mono<Map<String, Object>> visitCnt() {
        return kisedorkrVisitCntProcessor.execute();
    }
}
