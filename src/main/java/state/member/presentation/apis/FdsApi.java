package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.comm.StatTypes;
import state.member.application.fasade.FdsManager;
import state.member.application.fasade.TabCommStatsManager;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.entity.FdsDetCntByTypeStats;
import state.member.domain.entity.TabCommStats;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/fds")
@Controller
public class FdsApi {
    private final FdsManager fdsManager;
    private final TabCommStatsManager commManager;

    @GetMapping
    public String fdsView() {
        return "services/fds/fds";
    }

    @ResponseBody
    @GetMapping("/getFdsStatsCnt")
    public Mono<Map<String, Object>> getFdsStatsCnt() {
        // 유형별 탐지 건수 일별 리스트
        Mono<List<FdsDetCntByTypeStats>> detCntByTypeList = Mono.fromCallable(() -> fdsManager.getDetCntByTypeList());

        // 총 이상거래 탐지 건수 최근 30일 일별 리스트
        Mono<List<TabCommStats>> cntStatsList = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.FDS_TOT_CNT_DAILY.getStatType()));

        // 총 이상거래 탐지 건수 최근 6개월 월별 리스트
        Mono<List<TabCommStats>> totCntMonthlyList = Mono.fromCallable(() -> commManager.getMonthlyStatsList(StatTypes.FDS_TOT_CNT_MONTHLY.getStatType()));

        // 총 이상거래 탐지 건수 최근 3년 연도별 리스트
        Mono<List<TabCommStats>> totCntYearlyList = Mono.fromCallable(() -> commManager.getYearlyStatsList(StatTypes.FDS_TOT_CNT_YEARLY.getStatType()));

        return Mono.zip(detCntByTypeList, cntStatsList, totCntMonthlyList, totCntYearlyList)
                .map(tuple -> {
                    List<FdsDetCntByTypeStats> detCntByType = tuple.getT1();
                    List<TabCommStats> cntStats = tuple.getT2();
                    List<TabCommStats> totCntMonthly = tuple.getT3();
                    List<TabCommStats> totCntYearly = tuple.getT4();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("detCntByTypeList", detCntByType);
                    resultMap.put("cntStatsList", cntStats);
                    resultMap.put("totCntMonthlyList", totCntMonthly);
                    resultMap.put("totCntYearlyList", totCntYearly);

                    return resultMap;
                });
    }
}
