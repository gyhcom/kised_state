package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import state.member.application.fasade.StartbizManager;

import java.util.Map;

@RequestMapping("/startbiz")
@Controller
@RequiredArgsConstructor
public class StartbizApi {
    private final StartbizManager startbizManager;

    @GetMapping
    public String incorpView() {
        return "services/startbiz/startbiz";
    }

    /**
     * 법인설립건수, 방문자수
     * @return
     */
    @ResponseBody
    @GetMapping("/bizStatsInfoApi")
    public Mono<Map<String, Object>> bizStatsInfoApi() {
        return startbizManager.bizStatsInfoApi();
    }
}
