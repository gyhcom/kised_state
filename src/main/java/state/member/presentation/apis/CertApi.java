package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import state.member.application.fasade.CertManager;
import state.member.domain.entity.CertCountStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 창업기업확인
 */
@RequestMapping("/cert")
@Controller
@RequiredArgsConstructor
public class CertApi {
    private final CertManager certManager;

    @GetMapping
    public String certView() {
        return "services/cert/cert";
    }

    @ResponseBody
    @GetMapping("/certCnt")
    public Mono<Map<String, Object>> getCertCnt() {
        Mono<Map<String, Object>> certDailyCnt = certManager.getCertCnt();
        Mono<List<CertCountStatistics>> certDailyList = Mono.fromCallable(() -> certManager.getCertDailyList());

        return Mono.zip(certDailyCnt, certDailyList)
                .map(tuple -> {
                    Map<String, Object> external = tuple.getT1();
                    List<CertCountStatistics> dbData = tuple.getT2();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("certDailyCnt", external);
                    resultMap.put("certDailyList", dbData);

                    return resultMap;
                });
    }
}
