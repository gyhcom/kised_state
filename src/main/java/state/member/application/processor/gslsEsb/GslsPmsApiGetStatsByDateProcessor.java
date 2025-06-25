package state.member.application.processor.gslsEsb;

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
public class GslsPmsApiGetStatsByDateProcessor {
    private final ExternalUrlsConfig externalUrlsConfig;

    /**
     * 국고보조금(PMS) 외부 서버 API 호출
     * 1. 정보공시내역 건수
     * 2. 세입세출내역 건수
     * 3. 재무제표결산 내역 건수
     * 4. 수급자집행정보 건수
     * 5. 상세내역사업정보 건수
     * 일별, 월별, 연도별 데이터 조회
     * @return
     */
    public Mono<CommResponseCommand> execute() {
        return getWebClient(externalUrlsConfig.getGslsPms())
                .get()
                .uri("/api/stats/getGslsStatsByDate.do")
                .retrieve()
                .bodyToMono(CommResponseCommand.class)
                .timeout(Duration.ofSeconds(300)) // Timeout 설정
                .onErrorResume(e -> {
                    log.error("국고보조금(PMS) getGslsStatsByDate 데이터 호출 실패 : {}", e.getMessage());
                    return Mono.empty();
                });
    }
}
