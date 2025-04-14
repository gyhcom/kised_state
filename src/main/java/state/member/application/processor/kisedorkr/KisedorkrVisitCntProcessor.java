package state.member.application.processor.kisedorkr;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.config.ExternalUrlsConfig;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@AllArgsConstructor
@Component
public class KisedorkrVisitCntProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 기관 홈페이지 방문자 수
     * @return
     */
    public Mono<Map<String, Object>> execute() {
        return getWebClient(externalUrlsConfig.getKisedorkr())
                .get()
                .uri("/visitCnt.do")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("기관 홈페이지 visitCnt 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
