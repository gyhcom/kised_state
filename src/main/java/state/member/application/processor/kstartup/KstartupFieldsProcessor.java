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
public class KstartupFieldsProcessor {
    @Value("${services.service_2.url}")
    String serviceUrl;

    /**
     * 분야 정보(사업화, 기술개발(R&D) 등)
     * @return
     */
    public Flux<Map<String, Object>> execute() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getFields")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getDailyLoginCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
