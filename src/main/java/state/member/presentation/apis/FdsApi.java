package state.member.presentation.apis;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.FdsManager;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.entity.FdsDetCntByTypeStats;

import java.util.*;

@RequestMapping("/fds")
@Controller
public class FdsApi {
    private final FdsManager fdsManager;

    public FdsApi(FdsManager fdsManager) {
        this.fdsManager = fdsManager;
    }

    @GetMapping
    public String fdsView() {
        return "services/fds/fds";
    }

    @ResponseBody
    @GetMapping("/getDetInfo")
    public Flux<Map<String, Object>> getDetInfo(String year, String month) {
        return fdsManager.getDetInfo(year, month);
    }

    /**
     * 사전 탐지 건수, 사전 탐지 증빙 제출 건수, 총 이상거래 탐지 건수, 유형별 탐지 건수
     * 연도별, 월별
     * @param year
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllDetCnt")
    public Mono<List<List<Map<String, Object>>>> getAllDetCnt(@RequestParam String year) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(fdsManager.getDetAnnCnt());         // 연도별 사전 탐지 건수
        list.add(fdsManager.getDetMonCnt(year));     // 월별 사전 탐지 건수
        list.add(fdsManager.getDetProfAnnCnt());     // 연도별 사전 탐지 증빙 제출 건수
        list.add(fdsManager.getDetProfMonCnt(year)); // 월별 사전 탐지 증빙 제출 건수
        list.add(fdsManager.getTotDetAnnCnt());         // 연도별 총 이상거래탐지 건수
        list.add(fdsManager.getTotDetMonCnt(year));     // 월별 총 이상거래탐지 건수

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

    /**
     * 월별 사전 탐지 건수
     * @param year
     * @return
     */
    @ResponseBody
    @GetMapping("/getDetMonCnt")
    public Flux<Map<String, Object>> getDetMonCnt(@RequestParam String year) {
        return fdsManager.getDetMonCnt(year);
    }

    /**
     * 월별 사전 탐지 증빙 제출 건수
     * @param year
     * @return
     */
    @ResponseBody
    @GetMapping("/getDetProfMonCnt")
    public Flux<Map<String, Object>> getDetProfMonCnt(@RequestParam String year) {
        return fdsManager.getDetProfMonCnt(year);
    }

    /**
     * 월별 총 이상거래탐지 건수
     * @param year
     * @return
     */
    @ResponseBody
    @GetMapping("/getTotDetMonCnt")
    public Flux<Map<String, Object>> getTotDetMonCnt(@RequestParam String year) {
        return fdsManager.getTotDetMonCnt(year);
    }

    @ResponseBody
    @GetMapping("/getFdsStatsCnt")
    public Mono<Map<String, Object>> getFdsStatsCnt() {
        Mono<List<FdsCntStats>> cntStatsList = Mono.fromCallable(() -> fdsManager.getTotCntList());
        Mono<List<FdsDetCntByTypeStats>> detCntByTypeList = Mono.fromCallable(() -> fdsManager.getDetCntByTypeList());

        return Mono.zip(cntStatsList, detCntByTypeList)
                .map(tuple -> {
                    List<FdsCntStats> cntStats = tuple.getT1();
                    List<FdsDetCntByTypeStats> detCntByType = tuple.getT2();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("cntStatsList", cntStats);
                    resultMap.put("detCntByTypeList", detCntByType);

                    return resultMap;
                });
    }
}
