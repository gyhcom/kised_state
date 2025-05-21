package state.member.application.processor.startbiz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class StartbizBizStatsInfoApiProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 법인설립시스템
     * 법인 설립 건수, 방문자 수
     * @return
     */
    public Mono<Map<String, Object>> execute() {
        return getWebClient(externalUrlsConfig.getStartbiz())
                .get()
                .uri("/bizStatsInfoApi.do")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(Duration.ofSeconds(30))
                .onErrorResume(e -> {
                    log.error("법인설립 시스템 bizStatsInfoApi 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty();
                });
    }
}
