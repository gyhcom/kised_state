package state.member.application.processor.kstartup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.urls.ExternalUrlsConfig;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@AllArgsConstructor
@Component
public class KstartupApiGetMnpwCntProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * K-Startup
     * 회원 수 조회
     * @return
     */
    public Mono<Map<String, Object>> execute() {
        return getWebClient(externalUrlsConfig.getKstup())
                .get()
                .uri("/mnpwCnt.do")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-Startup mnpwCnt 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
