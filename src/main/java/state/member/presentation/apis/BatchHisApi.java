package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;
import state.member.application.fasade.BatchHisManager;
import state.member.domain.entity.BatchHis;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/batchHis")
@Controller
public class BatchHisApi {
    private final BatchHisManager batchHisManager;

    @GetMapping
    public String batchHisView() {
        return "services/batchHis/batchHis";
    }

    @ResponseBody
    @GetMapping("/getBatchHisList")
    public List<BatchHis> getBatchHisList(@RequestParam String stepName,
                                          @RequestParam String status,
                                          @RequestParam String startTime,
                                          @RequestParam String endTime) {
        return batchHisManager.getBatchHisList(stepName, status, startTime, endTime);
    }
}
