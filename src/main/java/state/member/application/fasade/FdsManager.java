package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.fds.*;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.entity.FdsDetCntByTypeStats;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class FdsManager {
    private final FdsDetInfoProcessor fdsDetInfoProcessor;
    private final FdsDetCntProcessor fdsDetCntProcessor;
    private final FdsDetProfCntProcessor fdsDetProfCntProcessor;
    private final FdsTotDetCntProcessor fdsTotDetCntProcessor;

    // API 연동 이후 사용
    private final FdsGetDetCntByTypeListProcessor fdsGetDetCntListProcessor;
    private final FdsGetTotDetCntListProcessor fdsGetTotDetCntListProcessor;
    private final FdsSaveTotDetCntProcessor fdsSaveTotDetCntProcessor;
    private final FdsSaveDetCntByTypeProcessor fdsSaveDetCntByTypeProcessor;
    private final FdsGetTotCntProcessor fdsGetTotCntProcessor;
    private final FdsGetDetCntByTypeProcessor fdsGetDetCntByTypeProcessor;

    public Flux<Map<String, Object>> getDetInfo(String year, String month) {
        return fdsDetInfoProcessor.execute(year, month);
    }

    public Flux<Map<String, Object>> getDetAnnCnt() {
        return fdsDetCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDetMonCnt(String year) {
        return fdsDetCntProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getDetProfAnnCnt() {
        return fdsDetProfCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDetProfMonCnt(String year) {
        return fdsDetProfCntProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getTotDetAnnCnt() {
        return fdsTotDetCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getTotDetMonCnt(String year) {
        return fdsTotDetCntProcessor.monthlyExecute(year);
    }

    // API 연동 이후

    // 외부 서버 API 호출 (총 이상거래 탐지 건수 API 호출)
    public Mono<Map<String, Object>> getTotDetCnt() {
        return fdsGetTotCntProcessor.execute();
    }

    public Mono<Map<String, Object>> getDetCntByType() {
        return fdsGetDetCntByTypeProcessor.execute();
    }

    public List<FdsCntStats> getTotCntList() {
        return fdsGetTotDetCntListProcessor.execute();
    }

    public List<FdsDetCntByTypeStats> getDetCntByTypeList() {
        return fdsGetDetCntListProcessor.execute();
    }

    public void saveTotCnt(FdsCntStats entity) {
        fdsSaveTotDetCntProcessor.execute(entity);
    }

    public void saveDetCntByType(List<FdsDetCntByTypeStats> entityList) {
        fdsSaveDetCntByTypeProcessor.execute(entityList);
    }

    public FdsCntStats mapToEntity(Map<String, Object> map) {
//        if( !validateMap(map) ) {
//            throw new ApiException(ErrorCode.NULL_POINT, "사업비점검 총 이상거래 탐지 건수 데이터를 확인해주세요.");
//        }

        Map<String, Object> resultDataMap = (Map<String, Object>) map.get("resultData");

        return FdsCntStats.builder()
                .detTotCnt(String.valueOf(resultDataMap.get("CNT")))
                .baseDt2((LocalDate) resultDataMap.get("baseDt2"))
                .build();
    }

    /**
     * 사업비점검 총 이상거래 탐지 건수 데이터 형식 체크
     * {
     *     "resultCode" : 0,
     *     "resultMessage" : "success",
     *     "resultData" : {
     *         "CNT" : 286
     *     }
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        Map<String, Object> resultDataMap = (Map<String, Object>) map.get("resultData");
        if( resultDataMap == null || resultDataMap.isEmpty() ) return false;

        boolean flag = true;

        try {
            Integer.parseInt(String.valueOf(resultDataMap.get("CNT")));
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    public List<FdsDetCntByTypeStats> listToEntity(Map<String, Object> map) {
//        if( !validateList(map) ) {
//            throw new ApiException(ErrorCode.NULL_POINT, "사업비점검 유형별 탐지 건수 데이터를 확인해주세요.");
//        }

        List<FdsDetCntByTypeStats> resultList = new ArrayList<>();

        for(Map<String, Object> dataMap : (List<Map<String, Object>>) map.get("resultData")) {
            resultList.add(
                    FdsDetCntByTypeStats.builder()
                            .detSeNm( String.valueOf(dataMap.get("DET_SE_NM")) )
                            .detTpCd( String.valueOf(dataMap.get("DET_TP_CD")) )
                            .detTpNm( String.valueOf(dataMap.get("DET_TP_NM")) )
                            .cnt( String.valueOf(dataMap.get("CNT")) )
                            .baseDt2((LocalDate) dataMap.get("baseDt2"))
                            .build()
            );
        }

        return resultList;
    }

    /**
     * 사업비점검 유형별 탐지 건수 데이터 형식 체크
     * {
     *     "resultCode" : "0",
     *     "resultMessage" : "success",
     *     "resultData" : [
     *          {
     *              "detTpCd" : "(ex) DJKA01",
     *              "detTpNm" : "협약기간 중 사업비 집행률 저조기업(비율)",
     *              "detSeNm" : "집행률",
     *              "cnt" : "6"
     *          },
     *          {
     *              "detTpCd" : "(ex) DJKA01",
     *              "detTpNm" : "당해년도 동일사업 수혜기업 간 거래",
     *              "detSeNm" : "비정상 거래",
     *              "cnt" : "3"
     *          } ,,,
     *     ]
     * }
     * @param map
     * @return
     */
    private Boolean validateList(Map<String, Object> map) {
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) map.get("resultData");
        if( resultDataMap == null || resultDataMap.isEmpty() ) return false;

        for(Map<String, Object> dataMap : resultDataMap) {

            for(Map.Entry<String, Object> entry : dataMap.entrySet()) {
                // 건수 체크 -> 정수로 변환 가능한 값인지 체크
                if(entry.getKey().equals("CNT")) {
                    try {
                        Integer.parseInt(String.valueOf(entry.getValue()));
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    // 그 외 다른 값은 Null 체크만 진행
                } else {
                    if(entry.getValue() == null || entry.getValue().equals("")) return false;
                }
            }
        }

        return true;
    }
}
