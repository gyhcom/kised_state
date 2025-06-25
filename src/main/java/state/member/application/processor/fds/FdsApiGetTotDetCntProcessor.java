package state.member.application.processor.fds;

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
public class FdsApiGetTotDetCntProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    public Mono<CommResponseCommand> execute() {
        return getWebClient(externalUrlsConfig.getFds())
                .get()
                .uri("/stats/getTotDetCntByDate.do")
                .retrieve()
                .bodyToMono(CommResponseCommand.class)
                .timeout(Duration.ofSeconds(120))
                .onErrorResume(e -> {
                    log.error("사업비점검 getTotDetCntByDate 데이터 호출 실패 : {}", e.getMessage());
                    return Mono.empty();
                });
    }
}
