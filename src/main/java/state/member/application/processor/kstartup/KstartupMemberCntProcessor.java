package state.member.application.processor.kstartup;

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
public class KstartupMemberCntProcessor {
    @Value("${services.service_2.url}")
    String serviceUrl;

    /**
     * 현재 회원수
     * @return
     */
    public Mono<Map<String, Object>> currentExecute() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getCurrentMemberCnt")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getCurrentMemberCnt 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }

    /**
     * 연도별 회원수
     * @return
     */
    public Mono<Map<String, Object>> annualExecute(String year) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getAnnualMemberCnt?year="+year)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getAnnualMemberCnt 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }

    /**
     * 월별 회원수
     * @param year
     * @return
     */
    public Mono<Map<String, Object>> monthlyExecute(String year, String month) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getMonthlyMemberCnt?year="+year+"&month="+month)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup TempMonthlyMemberCntDto 데이터 호출 실패: " + e.getMessage());
                    return Mono.empty(); // null 반환
                });
    }
}
