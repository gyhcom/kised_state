package state.member.application.processor.fds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class FdsTotDetCntProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;

    /**
     * 연도별 총 이상거래탐지 건수
     * @return
     */
    public Flux<Map<String, Object>> annualExecute() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getTotDetAnnCnt")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("FDS getTotDetAnnCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * 월별 총 이상거래탐지 건수
     * @param year
     * @return
     */
    public Flux<Map<String, Object>> monthlyExecute(String year) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getTotDetMonCnt?year="+year)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("FDS getTotDetMonCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
