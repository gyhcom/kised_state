package state.member.application.processor.kstartup;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.common.config.ExternalUrlsConfig;

import java.time.Duration;
import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@AllArgsConstructor
@Component
public class KstartupGetSearchStatusProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * K-Startup
     * 최근 7일 인기 검색어 목록
     * @return  
     */
    public Mono<Map<String, Object>> execute(String weekDaySe) {
        return getWebClient(externalUrlsConfig.getKstup())
                .get()
                .uri("/getSearchStatus.do?weekDaySe="+weekDaySe)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-Startup getSearchStatus 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
