package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.gslsEsb.*;
import state.member.domain.entity.GslsPmsCountStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class GslsEsbManager {
    private final GslsEsbGetInfoPubNotiProcessor gslsEsbGetInfoPubNotiProcessor;
    private final GslsEsbGetRveExptrProcessor gslsEsbGetRveExptrProcessor;
    private final GslsEsbGetFnlsttProcessor gslsEsbGetFnlsttProcessor;
    private final GslsEsbGetExcutKstupProcessor gslsEsbGetExcutKstupProcessor;
    private final GslsEsbGetDtlBsnsInfoProcessor gslsEsbGetDtlBsnsInfoProcessor;

    private final GslsPmsGetDailyCntProcessor gslsPmsGetDailyCntProcessor;
    private final GslsPmsGetLatesCntProcessor gslsPmsGetLatesCntProcessor;
    private final GslsPmsSaveDailyCntProcessor gslsPmsSaveDailyCntProcessor;
    private final GslsPmsAllStatCntProcessor gslsPmsAllStatCntProcessor;

    public Flux<Map<String, Object>> getInfoPubNotiAnnCnt() {
        return gslsEsbGetInfoPubNotiProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getInfoPubNotiMonCnt(String year) {
        return gslsEsbGetInfoPubNotiProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getInfoPubNoti(String year, String month, String searchValue) {
        return gslsEsbGetInfoPubNotiProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getRveExptrAnnCnt() {
        return gslsEsbGetRveExptrProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getRveExptrMonCnt(String year) {
        return gslsEsbGetRveExptrProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getRveExptr(String year, String month, String searchValue) {
        return gslsEsbGetRveExptrProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getFnlsttAnnCnt() {
        return gslsEsbGetFnlsttProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getFnlsttMonCnt(String year) {
        return gslsEsbGetFnlsttProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getFnlstt(String year, String month, String searchValue) {
        return gslsEsbGetFnlsttProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getExcutAnnCnt() {
        return gslsEsbGetExcutKstupProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getExcutMonCnt(String year) {
        return gslsEsbGetExcutKstupProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getExcutKstup(String year, String month, String searchValue) {
        return gslsEsbGetExcutKstupProcessor.dtlInfoExecute(year, month, searchValue);
    }

    public Flux<Map<String, Object>> getDtlBsnsInfoAnnCnt() {
        return gslsEsbGetDtlBsnsInfoProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDtlBsnsInfoMonCnt(String year) {
        return gslsEsbGetDtlBsnsInfoProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getDtlBsnsInfo(String year, String month, String searchValue) {
        return gslsEsbGetDtlBsnsInfoProcessor.dtlInfoExecute(year, month, searchValue);
    }

    // API 연동 이후
    public List<GslsPmsCountStatistics> getDailyCntList() {
        return gslsPmsGetDailyCntProcessor.execute();
    }

    public GslsPmsCountStatistics getLatesCnt() {
        return gslsPmsGetLatesCntProcessor.execute();
    }

    public  void saveDailyStat(GslsPmsCountStatistics entity) {
        gslsPmsSaveDailyCntProcessor.execute(entity);
    }

    public Mono<Map<String, Object>> getAllStatCnt() {
        return gslsPmsAllStatCntProcessor.execute();
    }

    public GslsPmsCountStatistics mapToEntity(Map<String, Object> map) {
//        if( !validateMap(map) ) {
//            throw new ApiException(ErrorCode.NULL_POINT, "국고보조금(PMS) 통계 데이터를 확인해주세요.");
//        }

        Map<String, Object> resultMap = (Map<String, Object>) map.get("map");

        return GslsPmsCountStatistics.builder()
                .excutCnt(String.valueOf(resultMap.get("excutCnt")))
                .ifpbntCnt(String.valueOf(resultMap.get("ifpbntCnt")))
                .anlrveCnt(String.valueOf(resultMap.get("anlrveCnt")))
                .fnlttCnt(String.valueOf(resultMap.get("fnlttCnt")))
                .ddtlbzCnt(String.valueOf(resultMap.get("ddtlbzCnt")))
                .baseDt2((LocalDate) resultMap.get("baseDt2"))
                .build();
    }

    /**
     * 국고보조금(PMS) 적재 데이터 형식 체크
     * {
     *     "list" : null,
     *     "map" : {
     *         "excutCnt" : 0,
     *         "ifpbntCnt" : 0,
     *         "anlrveCnt" : 0,
     *         "fnlttCnt" : 0,
     *         "ddtlbzCnt" : 0
     *     },
     *     "totCnt" : 0,
     *     "resultSb" : null,
     *     "result" : null,
     *     "resultMessage" : null
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        if( map == null || map.isEmpty() ) {
            return false;
        }

        if( map.getOrDefault("map", "N").equals("N") ) {
            return false;
        }

        Map<String, Object> statMap = (Map<String, Object>) map.get("map");

        for(Map.Entry<String, Object> entry : statMap.entrySet()) {
            // 건수 체크 -> 정수로 변환 가능한 값인지 체크
            try {
                Integer.parseInt(String.valueOf(entry.getValue()));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
}
