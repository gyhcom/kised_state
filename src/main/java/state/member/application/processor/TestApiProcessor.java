package state.member.application.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.config.ExternalUrlsConfig;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestApiProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    public Mono<Map<String, Object>> execute(LocalDate date) {
        return getWebClient(externalUrlsConfig.getCert())
                .get()
                .uri("/testApi.do?baseDt="+date)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("기관 홈페이지 visitCnt 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
