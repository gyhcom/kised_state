package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import state.member.application.comm.StatTypes;
import state.member.application.fasade.TabCommStatsManager;
import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comm")
@RestController
public class CommApi {
    private final TabCommStatsManager commManager;

    /**
     * 기간별 통계 조회
     * 기간별 조회 모달 화면 Grid에 조회될 데이터
     * detailModal.html. detailModal.js
     * @param statType
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/getDetailStats")
    public List<TabCommStats> getDetailStats(@RequestParam String statType,
                                             @RequestParam String startTime,
                                             @RequestParam String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return commManager.getStatsListByPeriod(statType, LocalDate.parse(startTime, formatter), LocalDate.parse(endTime, formatter));
    }

    @GetMapping("/getStatTypes")
    public List<Map<String, Object>> getStatTypes(@RequestParam String systemNm) {
        return Arrays.stream(StatTypes.values())
                .filter(systemName -> systemName.getSystemNm().contains(systemNm))
                .filter(systemName -> systemName.getStatType().toUpperCase().contains("DAILY"))
                .map(statTypes -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("typeNm", statTypes.getTypeNm());
                    resultMap.put("statType", statTypes.getStatType());
                    return resultMap;
                })
                .collect(Collectors.toList());
    }
}
