package state.member.presentation.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.DashboardManager;
import state.member.presentation.request.TempRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class DashBoardApi {
    private final DashboardManager dashboardManager;

    public DashBoardApi(DashboardManager dashboardManager) {
        this.dashboardManager = dashboardManager;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @ResponseBody
    @GetMapping("/getServicesData")
    public Mono<List<List<TempRequestDto>>> getEachServiceData() {
        List<Flux<TempRequestDto>> list = new ArrayList<>();
        list.add(dashboardManager.getSystems1UsageData());
        list.add(dashboardManager.getSystems2UsageData());

        //TODO WebFlux, WebClient에 대해 함께 설명이 있었다면 좋았을 것 같음
        // 납득시킬만한 내용이 있었으면 베스트
        // WebFlux에 대해 설명할 수 있는 문서를 작성했으면 좋겠음
        // 내부로 시스템 들여와야 함
        // 년도별 데이터를 가져올 수 있도록 구조를 잡고 데이터 건수가 화면에 노출되도록 수정 -> 차트가 변경되어도 됨.
        // 파이차트 백분율로 데이터가 넘어오지 않았을 때 어떻게 보여줄 건지

        // 모든 Flux를 Mono로 변환
        List<Mono<List<TempRequestDto>>> monoList = list.stream()
                .map(flux -> flux.collectList().defaultIfEmpty(new ArrayList<>()))
                .toList();

        // 병렬 처리로 모든 Mono 완료
        return Mono.zip(monoList, results ->
                Arrays.stream(results)
                        .map(result -> (List<TempRequestDto>) result)
                        .toList()
        );
    }
}
