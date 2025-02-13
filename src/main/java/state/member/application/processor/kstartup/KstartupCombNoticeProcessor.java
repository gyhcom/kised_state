package state.member.application.processor.kstartup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;
import static state.member.infrastructure.WebClientUtil.buildUriWithParams;

@Slf4j
@Component
public class KstartupCombNoticeProcessor {
    @Value("${services.service_2.url}")
    String serviceUrl;

    public Flux<Map<String, Object>> execute(Map<String, Object> searchData) {
        return getWebClient(serviceUrl)
                .get()
                .uri(uriBuilder -> buildUriWithParams(uriBuilder, "/getCombNotiReg", searchData))
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("K-startup getCombNotiReg 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
