package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.KisedorkrManager;
import state.member.domain.entity.CertCountStatistics;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.presentation.request.TempRequestDto;

import java.util.*;

/**
 * 기관 홈페이지 API
 */
@RequestMapping("/kisedorkr")
@Controller
public class KisedorkrApi {
    private final KisedorkrManager kisedorkrManager;

    public KisedorkrApi(KisedorkrManager kisedorkrManager) {
        this.kisedorkrManager = kisedorkrManager;
    }

    @GetMapping
    public String kisedorkrView() {
        return "services/kisedorkr/kisedorkr";
    }

    /**
     * 기관 홈페이지 방문자 수
     * @return
     */
    @ResponseBody
    @GetMapping("/visitCnt")
    public Mono<Map<String, Object>> visitCnt() {
        Mono<KisedorkrCountStatistics> dailyVisitCnt = Mono.fromCallable(() -> kisedorkrManager.getLatestVisitCnt());
        Mono<List<KisedorkrCountStatistics>> visitCntList =  Mono.fromCallable(() -> kisedorkrManager.getVisitCntList());

        return Mono.zip(dailyVisitCnt, visitCntList)
                .map(tuple -> {
                    KisedorkrCountStatistics external = tuple.getT1();
                    List<KisedorkrCountStatistics> dbData = tuple.getT2();

                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("visitCnt", external);
                    resultMap.put("visitCntList", dbData);

                    return resultMap;
                });
    }
}
