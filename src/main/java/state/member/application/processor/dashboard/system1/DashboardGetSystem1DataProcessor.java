package state.member.application.processor.dashboard.system1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import state.member.presentation.request.TempRequestDto;

import java.time.Duration;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class DashboardGetSystem1DataProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;
    Flux<TempRequestDto> data;
    public Flux<TempRequestDto> annualExecute() {
        data = null;
        try {
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
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }

    public Flux<TempRequestDto> monthlyExecute(String year) {
        data = null;
        try {
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
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }

    public Flux<TempRequestDto> weeklyExecute(String year, String month) {
        data = null;
        try {
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
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }
}
