package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.startbiz.StartbizBizStatsInfoApiProcessor;
import state.member.application.processor.startbiz.StartbizGetDailyCntListProcessor;
import state.member.application.processor.startbiz.StartbizGetLatestStatsProcessor;
import state.member.application.processor.startbiz.StartbizSaveDailyCntProcessor;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.domain.entity.StartbizCountStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class StartbizManager {
    private final StartbizBizStatsInfoApiProcessor startbizBizStatsInfoApiProcessor;
    private final StartbizGetDailyCntListProcessor startbizGetDailyCntListProcessor;
    private final StartbizSaveDailyCntProcessor startbizSaveDailyCntProcessor;
    private final StartbizGetLatestStatsProcessor startbizGetLatestStatsProcessor;

    public Mono<Map<String, Object>> bizStatsInfoApi() {
        return startbizBizStatsInfoApiProcessor.execute();
    }

    public List<StartbizCountStatistics> getDailyCntList() {
        return startbizGetDailyCntListProcessor.execute();
    }

    public void saveDailyCnt(StartbizCountStatistics entity) {
        startbizSaveDailyCntProcessor.execute(entity);
    }

    public StartbizCountStatistics getLatestStats() {
        return startbizGetLatestStatsProcessor.execute();
    }

    public StartbizCountStatistics mapToEntity(Map<String, Object> map) {
//        if( !validateMap(map) ) {
//            throw new ApiException(ErrorCode.NULL_POINT, "법인설립시스템 통계 데이터를 확인해주세요.");
//        }

        Map<String, Object> resultDataMap = (Map<String, Object>) map.get("stats");

        return StartbizCountStatistics.builder()
                .corpFndnCnt(String.valueOf(resultDataMap.get("corpFndnCnt")))
                .vstCnt(String.valueOf(resultDataMap.get("vstCnt")))
                .baseDt2((LocalDate) resultDataMap.get("baseDt2"))
                .build();
    }

    /**
     * 법인설립시스템 적재 데이터 체크
     * {
     *     "systmNm" : "STARTBIZ",
     *     "date" : "20250325(예시)"
     *     "stats" : {
     *         "corpFndnCnt" : 50,
     *         "vstCnt" : 2000
     *     }
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        // stats 자체가 없을 경우
        if( map.getOrDefault("stats", "N").equals("N") ) {
            return false;
        }

        // date 체크
        if( map.getOrDefault("date", "N").equals("N") ) {
            return false;
        }

        // stats는 있지만 "정수 형식" 값 체크
        Map<String, Object> stats = (Map<String, Object>) map.get("stats");
        if( stats.get("corpFndnCnt") instanceof Integer &&
            stats.get("vstCnt") instanceof Integer) {
            return true;
        }

        if ( stats.get("corpFndnCnt") instanceof String ) {
            try {
                Integer.parseInt((String) stats.get("corpFndnCnt"));
                return true;
            } catch (NumberFormatException e) {
                log.error("법인설립시스템 corpFndnCnt Format ERROR");
                return false;
            }
        }

        if ( stats.get("vstCnt") instanceof String ) {
            try {
                Integer.parseInt((String) stats.get("vstCnt"));
                return true;
            } catch (NumberFormatException e) {
                log.error("법인설립시스템 vstCnt Format ERROR");
                return false;
            }
        }

        return false;
    }
}
