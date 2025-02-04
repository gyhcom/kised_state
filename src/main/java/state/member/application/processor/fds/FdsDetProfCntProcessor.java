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
public class FdsDetProfCntProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;

    /**
     * 연도별 사전 탐지 증빙 제출 건수
     * @return
     */
    public Flux<Map<String, Object>> annualExecute() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getDetProfAnnCnt")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("FDS getDetProfAnnCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * 월별 사전 탐지 증빙 제출 건수
     * @param year
     * @return
     */
    public Flux<Map<String, Object>> monthlyExecute(String year) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getDetProfMonCnt?year="+year)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("FDS getDetProfMonCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
