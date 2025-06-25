package state.member.application.processor.startbiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import state.common.urls.ExternalUrlsConfig;
import state.member.application.command.CommResponseCommand;

import java.time.Duration;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartbizApiGetStatByDateProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 법인설립시스템 연, 월, 일 데이터 조회
     * 1. 방문자 수
     * 2. 법인 설립 건수
     * @return
     */
    public Mono<CommResponseCommand> execute() {
        return getWebClient(externalUrlsConfig.getStartbiz())
                .post()
                .uri("/bizStatsInfoPrrdApi.do")
                .retrieve()
                .bodyToMono(CommResponseCommand.class)
                .timeout(Duration.ofSeconds(300))
                .onErrorResume(e -> {
                    log.error("법인설립시스템 bizStatsInfoPrrdApi 데이터 호출 실패 : {}", e.getMessage());
                    return Mono.empty();
                });
    }
}
