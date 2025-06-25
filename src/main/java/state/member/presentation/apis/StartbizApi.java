package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import state.member.application.comm.StatTypes;
import state.member.application.fasade.StartbizManager;
import state.member.application.fasade.TabCommStatsManager;
import state.member.domain.entity.StartbizCountStatistics;
import state.member.domain.entity.TabCommStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/startbiz")
@Controller
public class StartbizApi {
    private final TabCommStatsManager commManager;

    @GetMapping
    public String incorpView() {
        return "services/startbiz/startbiz";
    }

    /**
     * 법인설립건수, 방문자수
     * @return
     */
    @ResponseBody
    @GetMapping("/bizStatsInfoApi")
    public Mono<Map<String, Object>> bizStatsInfoApi() {
        // 방문자 수 최근 30일 리스트
        Mono<List<TabCommStats>> visitCntDaily = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.STARTBIZ_VISIT_DAILY.getStatType()));

        // 방문자 수 최근 6개월 월별 리스트
        Mono<List<TabCommStats>> visitCntMonthly = Mono.fromCallable(() -> commManager.getMonthlyStatsList(StatTypes.STARTBIZ_VISIT_MONTHLY.getStatType()));

        // 방문자 수 최근 3년 연도별 리스트
        Mono<List<TabCommStats>> visitCntYearly = Mono.fromCallable(() -> commManager.getYearlyStatsList(StatTypes.STARTBIZ_VISIT_YEARLY.getStatType()));

        // 법인 설립 건수 최근 30일 리스트
        Mono<List<TabCommStats>> incorpCntDaily = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.STARTBIZ_CORP_FNDN_CNT_DAILY.getStatType()));

        // 법인 설립 건수 최근 6개월 월별 리스트
        Mono<List<TabCommStats>> incorpCntMonthly = Mono.fromCallable(() -> commManager.getMonthlyStatsList(StatTypes.STARTBIZ_CORP_FNDN_CNT_MONTHLY.getStatType()));

        // 법인 설립 건수 최근 3년 연도별 리스트
        Mono<List<TabCommStats>> incorpCntYearly = Mono.fromCallable(() -> commManager.getYearlyStatsList(StatTypes.STARTBIZ_CORP_FNDN_CNT_YEARLY.getStatType()));

        return Mono.zip(visitCntDaily, visitCntMonthly, visitCntYearly, incorpCntDaily, incorpCntMonthly, incorpCntYearly)
                .map(tuple -> {
                    List<TabCommStats> visitCntDailyList = tuple.getT1();
                    List<TabCommStats> visitCntMonthlyList = tuple.getT2();
                    List<TabCommStats> visitCntYearlyList = tuple.getT3();
                    List<TabCommStats> incorpCntDailyList = tuple.getT4();
                    List<TabCommStats> incorpCntMonthlyList = tuple.getT5();
                    List<TabCommStats> incorpCntYearlyList = tuple.getT6();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("visitCntDailyList", visitCntDailyList);
                    resultMap.put("visitCntMonthlyList", visitCntMonthlyList);
                    resultMap.put("visitCntYearlyList", visitCntYearlyList);
                    resultMap.put("incorpCntDailyList", incorpCntDailyList);
                    resultMap.put("incorpCntMonthlyList", incorpCntMonthlyList);
                    resultMap.put("incorpCntYearlyList", incorpCntYearlyList);

                    return resultMap;
                });
    }
}
