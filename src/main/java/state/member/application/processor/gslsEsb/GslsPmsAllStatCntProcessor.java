package state.member.application.processor.gslsEsb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.config.ExternalUrlsConfig;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class GslsPmsAllStatCntProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 국고보조금(PMS) 통계 조회
     * 1. 정보공시내역 건수
     * 2. 세입세출내역 건수
     * 3. 재무제표결산 내역 건수
     * 4. 수급자집행정보 건수
     * 5. 상세내역사업정보 건수
     * @return
     */
    public Mono<Map<String, Object>> execute() {
        return getWebClient(externalUrlsConfig.getGslsPms())
                .get()
                .uri("/api/stats/gslsStatistics.do")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("국고보조금(PMS) gslsStatistics 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
