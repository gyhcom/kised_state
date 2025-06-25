package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.comm.StatTypes;
import state.member.application.fasade.GslsEsbManager;
import state.member.application.fasade.TabCommStatsManager;
import state.member.domain.entity.GslsPmsCountStatistics;
import state.member.domain.entity.TabCommStats;

import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/gslsEsb")
@Controller
public class GslsEsbApi {
    private final GslsEsbManager gslsEsbManager;
    private final TabCommStatsManager commManager;

    @GetMapping("/pms")
    public String gslsEsbPmsView() {
        return "services/gslsEsb/gslsEsb-pms";
    }

    @GetMapping("/mis")
    public String gslsEsbMisView() {
        return "services/gslsEsb/gslsEsb-mis";
    }

    @ResponseBody
    @GetMapping("/getAllGslsEsbDtlInfo")
    public Mono<List<List<Map<String, Object>>>> getAllGslsEsbDtlInfo(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(gslsEsbManager.getInfoPubNoti(year, month, searchValue));
        list.add(gslsEsbManager.getRveExptr(year, month, searchValue));
        list.add(gslsEsbManager.getFnlstt(year, month, searchValue));
        list.add(gslsEsbManager.getExcutKstup(year, month, searchValue));
        list.add(gslsEsbManager.getDtlBsnsInfo(year, month, searchValue));

        List<Mono<List<Map<String, Object>>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<Map<String, Object>>) result)
                        .toList()
        );
    }

    @ResponseBody
    @GetMapping("/getInfoPubNoti")
    public Flux<Map<String, Object>> getInfoPubNoti(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        return gslsEsbManager.getInfoPubNoti(year, month, searchValue);
    }

    @ResponseBody
    @GetMapping("/getExcutKstup")
    public Flux<Map<String, Object>> getExcutKstup(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        return gslsEsbManager.getExcutKstup(year, month, searchValue);
    }

    @ResponseBody
    @GetMapping("/getFnlstt")
    public Flux<Map<String, Object>> getFnlstt(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        return gslsEsbManager.getFnlstt(year, month, searchValue);
    }

    @ResponseBody
    @GetMapping("/getRveExptr")
    public Flux<Map<String, Object>> getRveExptr(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        return gslsEsbManager.getRveExptr(year, month, searchValue);
    }

    @ResponseBody
    @GetMapping("/getDtlBsnsInfo")
    public Flux<Map<String, Object>> getDtlBsnsInfo(@RequestParam String year, @RequestParam String month, @RequestParam String searchValue) {
        return gslsEsbManager.getDtlBsnsInfo(year, month, searchValue);
    }

    // API 연동 이후

    @ResponseBody
    @GetMapping("/getAllGslsPmsStat")
    public Mono<Map<String, Object>> getAllGslsPmsStat() {
       // 국고보조금(PMS) 정보공시내역 최근 30일 통계 리스트
       Mono<List<TabCommStats>> ifpbntCntDailyList = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.GSLSPMS_IFPBNT_CNT_DAILY.getStatType()));

        // 국고보조금(PMS) 세입세출내역 최근 30일 통계 리스트
        Mono<List<TabCommStats>> anlrveCntDailyList = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.GSLSPMS_ANLRVE_CNT_DAILY.getStatType()));

        // 국고보조금(PMS) 재무제표결산서 최근 30일 통계 리스트
        Mono<List<TabCommStats>> fnlttCntDailyList = Mono.fromCallable(() -> commManager.getStatsTop30(StatTypes.GSLSPMS_FNLTT_CNT_DAILY.getStatType()));

        // 수급자집행정보 최근 6개월 월별 리스트
        Mono<List<TabCommStats>> excutCntMonthlyList = Mono.fromCallable(() -> commManager.getMonthlyStatsList(StatTypes.GSLSPMS_EXCUT_CNT_MONTHLY.getStatType()));

        // 상세내역사업정보 최근 3년 연도별 리스트
        Mono<List<TabCommStats>> ddtlbzCntYearlyList = Mono.fromCallable(() -> commManager.getYearlyStatsList(StatTypes.GSLSPMS_DDTLBZ_CNT_YEARLY.getStatType()));

        return Mono.zip(ifpbntCntDailyList, anlrveCntDailyList, fnlttCntDailyList, excutCntMonthlyList, ddtlbzCntYearlyList)
                .map(tuple -> {
                    List<TabCommStats> ifpbntCntDaily = tuple.getT1();
                    List<TabCommStats> anlrveCntDaily = tuple.getT2();
                    List<TabCommStats> fnlttCntDaily = tuple.getT3();
                    List<TabCommStats> excutCntMonthly = tuple.getT4();
                    List<TabCommStats> ddtlbzCntYearly = tuple.getT5();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("ifpbntCntDaily", ifpbntCntDaily);
                    resultMap.put("anlrveCntDaily", anlrveCntDaily);
                    resultMap.put("fnlttCntDaily", fnlttCntDaily);
                    resultMap.put("excutCntMonthly", excutCntMonthly);
                    resultMap.put("ddtlbzCntYearly", ddtlbzCntYearly);

                    return resultMap;
                });
    }
}
