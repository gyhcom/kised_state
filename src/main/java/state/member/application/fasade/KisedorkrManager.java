package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.kisedorkr.*;
import state.member.domain.entity.KisedorkrCountStatistics;

import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class KisedorkrManager {
    private final KisedorkrApiGetVisitCntProcessor kisedorkrApiGetVisitCntProcessor;
    private final KisedorkrSaveVisitCntProcessor kisedorkrSaveVisitCntProcessor;
    private final KisedorkrGetVisitCntListProcessor kisedorkrGetVisitCntListProcessor;
    private final KisedorkrGetLatestVisitCntProcessor kisedorkrGetLatestVisitCntProcessor;

    // (기관 홈페이지 API) 로그인 수 조회
    public Mono<Map<String, Object>> visitCnt() {
        return kisedorkrApiGetVisitCntProcessor.execute();
    }

    // (일일 배치) 기관 홈페이지 방문자 수 저장
    public void saveVisitCnt(KisedorkrCountStatistics entity) {
        kisedorkrSaveVisitCntProcessor.execute(entity);
    }

    // 일일 방문자수 최근 30건 조회
    public List<KisedorkrCountStatistics> getVisitCntList() {
        return kisedorkrGetVisitCntListProcessor.execute();
    }

    // (통계 DB) 일일 방문자 수 최근 1건 조회
    public KisedorkrCountStatistics getLatestVisitCnt() {
        return kisedorkrGetLatestVisitCntProcessor.execute();
    }

    public KisedorkrCountStatistics mapToEntity(Map<String, Object> map) {
        if( !validateMap(map) ) {
            throw new ApiException(ErrorCode.NULL_POINT, "기관 홈페이지 통계 데이터를 확인해주세요.");
        }

        Map<String, Object> resultDataMap = (Map<String, Object>) map.get("resultData");

        return KisedorkrCountStatistics.builder()
                .vstCnt(String.valueOf(resultDataMap.get("cnt")))
                .build();
    }

    /**
     * 기관 홈페이지 적재 데이터 체크
     * {
     *     "resultCode" : "0",
     *     "resultMessage" : "success"
     *     "resultData" : {
     *         "cnt" : 1
     *     }
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        // resultData 자체가 없을 경우
        if( map.getOrDefault("resultData", "N").equals("N") ) {
            return false;
        }

        // resultData는 있지만 "정수 형식" 값 체크
        Map<String, Object> resultData = (Map<String, Object>) map.get("resultData");
        if( resultData.get("cnt") instanceof Integer ) {
            return true;
        }

        if ( resultData.get("cnt") instanceof String ) {
            try {
                Integer.parseInt((String) resultData.get("cnt"));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }
}
