package state.member.application.processor.fds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class FdsDetInfoProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;

    /**
     * 이상거래 유형별 탐지 건수
     * @return
     */
    public Flux<Map<String, Object>> execute(String year, String month) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getDetInfo?year="+year+"&month="+month)
                .retrieve() //서버에 요청을 전송하고 응답을 받을 준비를 합니다.
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {}) //응답 본문을 Flux로 변환
                //.limitRate(1000) //데이터 지연 처리 -> 10건 씩 처리함
                //.timeout(Duration.ofSeconds(30)) // Timeout 설정
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("FDS getDetInfo 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
