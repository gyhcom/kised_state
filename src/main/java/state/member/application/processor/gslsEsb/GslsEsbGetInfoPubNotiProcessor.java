package state.member.application.processor.gslsEsb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static state.member.infrastructure.WebClientHandler.getWebClient;

@Slf4j
@Component
public class GslsEsbGetInfoPubNotiProcessor {
    @Value("${services.service_1.url}")
    String serviceUrl;

    /**
     * 국고보조금
     * 연도별 정보공시내역 건수
     * @return
     */
    public Flux<Map<String, Object>> annualExecute() {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getInfoPubNotiAnnCnt")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("GslsEsb getInfoPubNotiAnnCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * 국고보조금
     * 월별 정보공시내역 건수
     * @return
     */
    public Flux<Map<String, Object>> monthlyExecute(String year) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getInfoPubNotiMonCnt?year="+year)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("GslsEsb getInfoPubNotiMonCnt 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }

    /**
     * 정보공시 내역
     * @param year
     * @param month
     * @return
     */
    public Flux<Map<String, Object>> dtlInfoExecute(String year, String month, String searchValue) {
        return getWebClient(serviceUrl)
                .get()
                .uri("/getInfoPubNoti?year="+year+"&month="+month+"&searchValue="+searchValue)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> { // 에러 발생 시 null 반환
                    log.error("GslsEsb getInfoPubNoti 데이터 호출 실패: " + e.getMessage());
                    return Flux.empty(); // null 반환
                });
    }
}
