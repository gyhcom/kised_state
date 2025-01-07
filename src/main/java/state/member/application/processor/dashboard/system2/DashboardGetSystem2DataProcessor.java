package state.member.application.processor.dashboard.system2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import state.member.presentation.request.TempRequestDto;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class DashboardGetSystem2DataProcessor {
    @Value("${services.service_2.url}")
    String serviceUrl;

    Flux<TempRequestDto> data;
    public Flux<TempRequestDto> annualExecute() {
        data = null;
        try {
            data = getWebClient(serviceUrl)
                    .get()
                    .uri("/getData")
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .limitRate(10) //데이터 지연 처리 -> 10건 씩 처리함
                    //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        log.error("System 2 getData 데이터 호출 실패: " + e.getMessage());
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
                        log.error("System 2 getMonthlyData 데이터 호출 실패: " + e.getMessage());
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
                        log.error("System 2 getWeeklyData 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }
}
