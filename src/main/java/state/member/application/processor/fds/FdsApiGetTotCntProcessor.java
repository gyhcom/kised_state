package state.member.application.processor.fds;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.urls.ExternalUrlsConfig;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class FdsApiGetTotCntProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 사업비점검 이상거래탐지 외부 서버 API 호출
     * 총 이상거래 탐지 건수
     * @return
     */
    public Mono<Map<String, Object>> execute() {
        return getWebClient(externalUrlsConfig.getFds())
                .get()
                .uri("/stats/totDetCnt.do")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("사업비점검 cntEachType 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
