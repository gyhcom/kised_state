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

    public Flux<TempRequestDto> execute() {
        WebClient service_2_conn = getWebClient(serviceUrl);

        Flux<TempRequestDto> data = null;
        try {
            data = service_2_conn
                    .get()
                    .uri("/getData")
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        System.err.println("System 2 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }
}
