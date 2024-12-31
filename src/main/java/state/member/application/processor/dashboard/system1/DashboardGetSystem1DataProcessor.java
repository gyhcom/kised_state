package state.member.application.processor.dashboard.system1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import state.member.presentation.request.TempRequestDto;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class DashboardGetSystem1DataProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;

    public Flux<TempRequestDto> execute() {
        //TODO 특정 시스템 데이터 추출 과정에서 에러 발생했을 때 어떻게 처리할 지 생각하기
        WebClient service_1_conn = getWebClient(serviceUrl);

        Flux<TempRequestDto> data = null;
        try {
            data = service_1_conn
                    .get()
                    .uri("/getData")
                    .retrieve()
                    .bodyToFlux(TempRequestDto.class)
                    .onErrorResume(e -> { // 에러 발생 시 null 반환
                        System.err.println("System 1 데이터 호출 실패: " + e.getMessage());
                        return Flux.empty(); // null 반환
                    });
        } catch(Exception e) {
            log.info("External API ERROR : " + e.getMessage());
        }

        return data;
    }


//    return service_1.post()
//            .uri("/getData")
//                .retrieve()
//                .onStatus(status -> status.is4xxClientError(),
//    response -> Mono.error(new RuntimeException("클라이언트 오류 발생")))
//            .onStatus(status -> status.is5xxServerError(),
//    response -> Mono.error(new RuntimeException("서버 오류 발생")))
//            .bodyToMono(String.class)
//                .doOnError(WebClientResponseException.class, ex -> {
//        log.error("에러 발생: {}", ex.getMessage());
//    });
}
