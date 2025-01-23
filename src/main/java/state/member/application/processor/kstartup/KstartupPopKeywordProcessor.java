package state.member.application.processor.kstartup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class KstartupPopKeywordProcessor {
    @Value("${services.service_2.url}")
    String serviceUrl;

    /**
     * k-startup 연간 인기 검색 키워드 Top10
     * @return
     */
    public Flux<Map<String, Object>> annualExecute(String year) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getAnnualPopKeyword?year="+year)
                .retrieve() //서버에 요청을 전송하고 응답을 받을 준비를 합니다.
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {}) //응답 본문을 Flux로 변환
                //.limitRate(1000) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getAnnualPopKeyword 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * k-startup 월간 인기 검색 키워드 Top10
     * @return
     */
    public Flux<Map<String, Object>> monthlyExecute(String year, String month) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getMonthlyPopKeyword?year="+year+"&month="+month)
                .retrieve() //서버에 요청을 전송하고 응답을 받을 준비를 합니다.
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {}) //응답 본문을 Flux로 변환
                //.limitRate(1000) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getMonthlyPopKeyword 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * k-startup 주간 인기 검색 키워드 Top10
     * @return
     */
    public Flux<Map<String, Object>> weeklyExecute(String year, String month) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getWeeklyPopKeyword?year="+year+"&month="+month)
                .retrieve() //서버에 요청을 전송하고 응답을 받을 준비를 합니다.
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {}) //응답 본문을 Flux로 변환
                //.limitRate(1000) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getWeeklyPopKeyword 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
