package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.KisedorkrManager;
import state.member.presentation.request.TempRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 기관 홈페이지 API
 */
@RequestMapping("/kisedorkr")
@Controller
public class KisedorkrApi {
    private final KisedorkrManager kisedorkrManager;

    public KisedorkrApi(KisedorkrManager kisedorkrManager) {
        this.kisedorkrManager = kisedorkrManager;
    }

    @GetMapping
    public String kisedorkrView() {
        return "services/kisedorkr";
    }

    /**
     * 서비스의 년도별, 월별, 주별 데이터를 모두 가져온다
     * @return
     */
    @ResponseBody
    @GetMapping("/getAnnualData")
    public Mono<List<List<Map<String, Object>>>> getEachServiceData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kisedorkrManager.getAnnulData());
        list.add(kisedorkrManager.getMonthlyData(year));
        list.add(kisedorkrManager.getWeeklyData(year, month));

        //TODO WebFlux, WebClient에 대해 함께 설명이 있었다면 좋았을 것 같음
        // 납득시킬만한 내용이 있었으면 베스트
        // WebFlux에 대해 설명할 수 있는 문서를 작성했으면 좋겠음
        // 내부로 시스템 들여와야 함
        // 년도별 데이터를 가져올 수 있도록 구조를 잡고 데이터 건수가 화면에 노출되도록 수정 -> 차트가 변경되어도 됨.
        // 파이차트 백분율로 데이터가 넘어오지 않았을 때 어떻게 보여줄 건지

        // 모든 Flux를 Mono로 변환
        /**
         * 왜 Mono로 변환했는지?
         *
         * 최종 데이터를 리스트로 다루기 쉽게 하기 위해서 -> Flux -> Mono<List<TempRequestDto>>
         * 병렬 처리 및 동기화 작업을 쉽게 하기 위해서
         * .collectList() : Flux로 흐르는 데이터를 한 번에 수집하여 리스트(List<TempRequestDto>) 만듦
         * .defaultIfEmpty : 만약 넘어온 데이터가 없을 경우 빈 리스트 세팅한다
         */
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
    @GetMapping("/getMonthlyData")
    public Mono<List<List<Map<String, Object>>>> getMonthlyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kisedorkrManager.getMonthlyData(year));
        list.add(kisedorkrManager.getWeeklyData(year, month));

        // 모든 Flux를 Mono로 변환
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
    @GetMapping("/getWeeklyData")
    public Mono<List<List<Map<String, Object>>>> getWeeklyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<Map<String, Object>>> list = new ArrayList<>();
        list.add(kisedorkrManager.getWeeklyData(year, month));

        // 모든 Flux를 Mono로 변환
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
    @GetMapping("/getGridData")
    public Flux<Map<String, Object>> getGridData() {
        return kisedorkrManager.getGridData();
    }

    /**
     * 기관 홈페이지 방문자 수
     * @return
     */
    @ResponseBody
    @GetMapping("/visitCnt")
    public Mono<Map<String, Object>> visitCnt() {
        return kisedorkrManager.visitCnt();
    }
}
