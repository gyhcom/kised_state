package state.member.presentation.apis;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.KstupManager;
import state.member.application.fasade.TabCommStatsManager;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.entity.KstupInstPbancRegStatistics;
import state.member.domain.entity.TabCommStats;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kstup")
@Controller
public class KstartupApi {
    private final KstupManager kstupManager;
    private final TabCommStatsManager commManager;

    /**
     * 회원 & 로그인수 화면
     * @return
     */
    @GetMapping("/membAndLogin")
    public String kstupMembLoginView() {
        return "services/kstartup/kstup-memb-login-cnt";
    }

    /**
     * 공고등록 통계 화면
     * @return
     */
    @GetMapping("/institutionStats")
    public String institutionStatsView() {
        return "services/kstartup/kstup-institutionStats";
    }

    /**
     * K-Startup 기관유형별 사업공고 등록 건수 조회
     * @return
     */
    @ResponseBody
    @GetMapping("/instBizPbancRegCnt")
    Mono<Map<String, Object>> instBizPbancRegCnt() {
        // 기관유형별 사업공고 리스트 -> 유형별 리스트 분류
        List<KstupInstPbancRegStatistics> instPbancRegCntList = kstupManager.getBizPbancRegCntList();
        Mono<Map<String, List<KstupInstPbancRegStatistics>>> groupedList = Mono.fromCallable(() -> instPbancRegCntList.stream()
                .collect(Collectors.groupingBy(entity -> {
                    String instNm = entity.getInstNm();
                    return switch (instNm) {
                        case "민간" -> "private";
                        case "공공기관" -> "public";
                        case "지자체"  -> "local";
                        case "교육기관"  -> "edu";
                        default     -> "etc";
                    };
                })));

        // 일일 적재 데이터 리스트 조회
        Mono<List<KstupCountStatistics>> dailyList = Mono.fromCallable(() -> kstupManager.getDailyCntList());

        // 통합공고 등록 건수 (실시간)
        Mono<Map<String, Object>> intgPbancRegCnt = kstupManager.intgPbancRegCnt();

        // 사업공고 등록 기관 수 (실시간)
        Mono<Map<String, Object>> bizPbancRegCnt = kstupManager.bizPbancRegInstCnt();

        return Mono.zip(groupedList, dailyList, intgPbancRegCnt, bizPbancRegCnt)
                .map(tuple -> {
                    Map<String, List<KstupInstPbancRegStatistics>> instPbancRegCntMap = tuple.getT1();
                    List<KstupCountStatistics> dailyCntList = tuple.getT2();
                    Map<String, Object> intgPbancRegCntMap = tuple.getT3();
                    Map<String, Object> bizPbancRegCntMap = tuple.getT4();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("instPbancRegCntMap", instPbancRegCntMap);
                    resultMap.put("dailyCntList", dailyCntList);
                    resultMap.put("intgPbancRegCnt", intgPbancRegCntMap);
                    resultMap.put("bizPbancRegCnt", bizPbancRegCntMap);

                    return resultMap;
                });
    }

    /**
     * K-Startup 사용자 활동 관련 건수 화면 데이터
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserActStatistics")
    public Mono<Map<String, Object>> getDailyCntList() {
        Mono<Map<String, Object>> userCntMap = kstupManager.mnpwCnt();     // 회원 수
        Mono<KstupCountStatistics> lginCntMap = Mono.fromCallable(() -> kstupManager.getLatestLoginCnt());     // (통계 DB 조회) 최근 로그인 수, 로그인 수(중복제거) 1건 조회
        Mono<List<KstupCountStatistics>> dailyCntListMap = Mono.fromCallable(() -> kstupManager.getDailyCntList()); // 사용자 활동 일일 데이터 리스트
        Mono<Map<String, Object>> dailyPopKeywordMap = kstupManager.getSearchStatus("day");    // 일일 인기검색어
        Mono<Map<String, Object>> weeklyPopKeywordMap = kstupManager.getSearchStatus("week");  // 최근 7일 인기검색어

        return Mono.zip(userCntMap, lginCntMap, dailyCntListMap, dailyPopKeywordMap, weeklyPopKeywordMap)
                .map(tuple -> {
                    Map<String, Object> userCnt = tuple.getT1();
                    KstupCountStatistics lginCnt = tuple.getT2();
                    List<KstupCountStatistics> dailyCntList = tuple.getT3();
                    Map<String, Object> dailyPopKeyword = tuple.getT4();
                    Map<String, Object> weeklyPopKeyword = tuple.getT5();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("userCnt", userCnt);
                    resultMap.put("lginCnt", lginCnt);
                    resultMap.put("dailyCntList", dailyCntList);
                    resultMap.put("dailyPopKeyword", dailyPopKeyword);
                    resultMap.put("weeklyPopKeyword", weeklyPopKeyword);

                    return resultMap;
                });
    }
}
