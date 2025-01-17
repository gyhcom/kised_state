package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.DashboardManager;
import state.member.presentation.request.TempRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
@Controller
@RequestMapping("/service2")
public class Service2Api {
    private final DashboardManager dashboardManager;

    public Service2Api(DashboardManager dashboardManager) {
        this.dashboardManager = dashboardManager;
    }

    /**
     * 서비스의 년도별, 월별, 주별 데이터를 모두 가져온다
     * @return
     */
    @ResponseBody
    @GetMapping("/getServicesData")
    public Mono<List<List<TempRequestDto>>> getEachServiceData(@RequestParam String year, @RequestParam String month) {
        List<Flux<TempRequestDto>> list = new ArrayList<>();
        list.add(dashboardManager.getSystems2AnnulData());
        list.add(dashboardManager.getSystem2MonthlyData(year));
        list.add(dashboardManager.getSystem2WeeklyData(year, month));

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

    @ResponseBody
    @GetMapping("/getMonthlyData")
    public Mono<List<List<TempRequestDto>>> getMonthlyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<TempRequestDto>> list = new ArrayList<>();
        list.add(dashboardManager.getSystem2MonthlyData(year));
        list.add(dashboardManager.getSystem2WeeklyData(year, month));

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

    @ResponseBody
    @GetMapping("/getWeeklyData")
    public Mono<List<List<TempRequestDto>>> getWeeklyData(@RequestParam String year, @RequestParam String month) {
        List<Flux<TempRequestDto>> list = new ArrayList<>();
        list.add(dashboardManager.getSystem2WeeklyData(year, month));

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
