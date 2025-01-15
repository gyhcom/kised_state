package state.member.application.processor.dashboard.system1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import state.member.presentation.request.TempRequestDto;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class DashboardGetSystem1DataProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;
    Flux<TempRequestDto> data;
    public Flux<TempRequestDto> annualExecute() {
        /**
         * 이 부분에선 데이터를 가져오기만 했고, 가져온 데이터를 사용하기 위해선, .subscribe() or .collectList()로
         * 데이터를 소비(사용)해야한다. -> Service1Api.java, Service2Api.java
         */
        data = getWebClient(serviceUrl)
                .get()
                .uri("/getData")
                .retrieve() //서버에 요청을 전송하고 응답을 받을 준비를 합니다.
                .bodyToFlux(TempRequestDto.class) //응답 본문을 Flux로 변환
                .limitRate(10) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("System 1 getData 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });

        return data;
    }

    public Flux<TempRequestDto> monthlyExecute(String year) {
        data = getWebClient(serviceUrl)
                .get()
                .uri("/getMonthlyData?year="+year)
                .retrieve()
                .bodyToFlux(TempRequestDto.class)
                .limitRate(10) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("System 1 getMonthlyData 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });

        return data;
    }

    public Flux<TempRequestDto> weeklyExecute(String year, String month) {
        data = getWebClient(serviceUrl)
                .get()
                .uri("/getWeeklyData?year="+year+"&month="+month)
                .retrieve()
                .bodyToFlux(TempRequestDto.class)
                .limitRate(10) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("System 1 getWeeklyData 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });

        return data;
    }

    public Flux<Map<String, Object>> getGridData() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getGridData")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .limitRate(10) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("System 1 getGridData 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
