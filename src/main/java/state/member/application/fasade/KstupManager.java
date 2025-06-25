package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.kstartup.*;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.entity.KstupInstPbancRegStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class KstupManager {
    private final KstartupApiGetLginCntProcessor kstartupApiGetLginCntProcessor;
    private final KstartupApiGetBizPbancRegInstCntProcessor kstartupApiGetBizPbancRegInstCntProcessor;
    private final KstartupApiGetSearchStatusProcessor kstartupApiGetSearchStatusProcessor;
    private final KstartupApiGetInstBizPbancRegCntProcessor kstartupApiGetInstBizPbancRegCntProcessor;
    private final KstartupApiGetIntgPbancRegCntProcessor kstartupApiGetIntgPbancRegCntProcessor;
    private final KstartupApiGetMnpwCntProcessor kstartupApiGetMnpwCntProcessor;

    private final KstartupSaveDailyCntProcessor kstartupSaveDailyCntProcessor;
    private final KstartupGetDailyCntListProcessor kstartupGetDailyCntListProcessor;

    private final KstartupSaveBizPbancRegCntListProcessor kstartupSaveBizPbancRegCntListProcessor;
    private final KstartupGetBizPbancRegCntListProcessor kstartupGetBizPbancRegCntListProcessor;
    private final KstartupGetLatestLoginCntProcessor kstartupGetLatestLoginCntProcessor;

    /**
     * API 제공 후 사용
     */
    // 로그인 수 조회
    public Mono<Map<String, Object>> lginCnt() {
        return kstartupApiGetLginCntProcessor.execute();
    }

    // 통합공고 등록 건수 조회
    public Mono<Map<String, Object>> intgPbancRegCnt() {
        return kstartupApiGetIntgPbancRegCntProcessor.execute();
    }

    // 사업공고 등록 기관(주관기관) 수 조회
    public Mono<Map<String, Object>> bizPbancRegInstCnt() {
        return kstartupApiGetBizPbancRegInstCntProcessor.execute();
    }

    // 기관유형별 사업공고 등록 건수 조회
    public Mono<Map<String, Object>> instBizPbancRegCnt() {
        return kstartupApiGetInstBizPbancRegCntProcessor.execute();
    }

    // 최근 7일 인기 검색어 목록
    public Mono<Map<String, Object>> getSearchStatus(String weekDaySe) {
        return kstartupApiGetSearchStatusProcessor.execute(weekDaySe);
    }

    // 회원 수 조회
    public Mono<Map<String, Object>> mnpwCnt() {
        return kstartupApiGetMnpwCntProcessor.execute();
    }

    // (일일 배치) 단건 데이터 적재
    public void saveDailyCnt(KstupCountStatistics entity) {
        kstartupSaveDailyCntProcessor.execute(entity);
    }

    // 일일 데이터 리스트 조회
    public List<KstupCountStatistics> getDailyCntList() {
        return kstartupGetDailyCntListProcessor.execute();
    }

    // (현황 통계 DB) 기관유형별 사업공고 등록 건수 리스트 조회
    public List<KstupInstPbancRegStatistics> getBizPbancRegCntList() {
        return kstartupGetBizPbancRegCntListProcessor.execute();
    }

    // (일일 배치) 기관유형별 사업공고 등록 건수 리스트 저장
    public void saveBizPbancRegCntList(List<KstupInstPbancRegStatistics> entityList) {
        kstartupSaveBizPbancRegCntListProcessor.execute(entityList);
    }

    // (현황 통계 DB) 최근 로그인 수 조회
    public KstupCountStatistics getLatestLoginCnt() {
        return kstartupGetLatestLoginCntProcessor.execute();
    }

    // K-startup 단건 데이터 mapToEntity
    public KstupCountStatistics mapToEntity(Map<String, Object> map) {
        if( !validateMap(map) ) {
            throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 통계 데이터를 확인해주세요.");
        }

        Map<String, Object> lginMap = (Map<String, Object>) map.get("lginCnt");
        Map<String, Object> intgPbancRegMap = (Map<String, Object>) map.get("intgPbancRegCnt");
        Map<String, Object> bizPbancRegInstMap = (Map<String, Object>) map.get("bizPbancRegInstCnt");
        Map<String, Object> mnpwMap = (Map<String, Object>) map.get("mnpwCnt");

        return KstupCountStatistics.builder()
                .lginCnt(String.valueOf(lginMap.get("lginCnt")))
                .lginDupCnt(String.valueOf(lginMap.get("lginDuplCnt")))
                .intgPbancRegCnt(String.valueOf(intgPbancRegMap.get("cnt")))
                .bizPbancRegInstCnt(String.valueOf(bizPbancRegInstMap.get("cnt")))
                .mnpwCnt(String.valueOf(mnpwMap.get("cnt")))
                .build();
    }

    /**
     * K-Startup 적재 데이터 체크
     * {
     *     "resultCode" : "0",
     *     "resultMessage" : "success"
     *     "resultData" : {
     *         "cnt" : "1"
     *     }
     * }
     *  OR
     * {
     *      "resultCode" : "0",
     *      "resultMessage" : "success"
     *      "resultData" : {
     *          "lginCnt" : "1",
     *          "lginDuplCnt" : "1"
     *      }
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        Boolean result = false;

        // ================ 로그인 수 Validation 체크 시작 ====================
        Map<String, Object> lginData = (Map<String, Object>) map.get("lginCnt");
        if( lginData.get("lginCnt") instanceof Integer &&
            lginData.get("lginDuplCnt") instanceof Integer) {
            result = true;
        }

        if ( lginData.get("lginCnt") instanceof String ) {
            try {
                Integer.parseInt((String) lginData.get("lginCnt"));
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
        }

        if ( lginData.get("lginDuplCnt") instanceof String ) {
            try {
                Integer.parseInt((String) lginData.get("lginDuplCnt"));
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
        }
        // ================ 로그인 수 Validation 체크 종료 ====================

        // ================ 통합공고 등록 건수 Validation 체크 시작 ====================
        Map<String, Object> intgPbancRegMap = (Map<String, Object>) map.get("intgPbancRegCnt");
        if( intgPbancRegMap.get("cnt") instanceof Integer ) {
            result = true;
        }

        if ( intgPbancRegMap.get("cnt") instanceof String ) {
            try {
                Integer.parseInt((String) intgPbancRegMap.get("cnt"));
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
        }
        // ================ 통합공고 등록 건수 Validation 체크 종료 ====================

        // ================ 사업공고 등록 기관 수 Validation 체크 시작 ====================
        Map<String, Object> bizPbancRegInstMap = (Map<String, Object>) map.get("bizPbancRegInstCnt");
        if( bizPbancRegInstMap.get("cnt") instanceof Integer ) {
            result = true;
        }

        if ( bizPbancRegInstMap.get("cnt") instanceof String ) {
            try {
                Integer.parseInt((String) bizPbancRegInstMap.get("cnt"));
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
        }
        // ================ 사업공고 등록 기관 수 Validation 체크 종료 ====================

        // ================ 회원 수 Validation 체크 시작 ====================
        Map<String, Object> mnpwMap = (Map<String, Object>) map.get("mnpwCnt");
        if( mnpwMap.get("cnt") instanceof Integer ) {
            result = true;
        }

        if ( mnpwMap.get("cnt") instanceof String ) {
            try {
                Integer.parseInt((String) mnpwMap.get("cnt"));
                result = true;
            } catch (NumberFormatException e) {
                result = false;
            }
        }
        // ================ 회원 수 Validation 체크 종료 ====================

        return result;
    }

    // K-Startup 기관유형별 사업공고 등록 건수 리스트 listToEntity -> 실제로 적재되는 resultData가 List이기 때문에 listToEntity
    public List<KstupInstPbancRegStatistics> listToEntity(Map<String, Object> map) {
        if( !validateList(map) ) {
            log.info("RESPONSE DATA ::: " + map.toString());
            throw new ApiException(ErrorCode.NULL_POINT, "K-Startup 기관유형별 사업공고 등록 건수 API 응답 데이터를 확인해주세요.");
        }

        List<KstupInstPbancRegStatistics> resultList = new ArrayList<>();

        for(Map<String, Object> dataMap : (List<Map<String, Object>>) map.get("resultData")) {
            resultList.add(
                KstupInstPbancRegStatistics.builder()
                        .bizPbancRegCnt(String.valueOf(dataMap.get("cnt")))
                        .instNm(String.valueOf(dataMap.get("title")))
                        .build()
            );
        }

        return resultList;
    }

    /**
     * K-Startup 기관유형별 사업공고 등록 건수 데이터 구조
     * {
     *     "resultCode" : "0",
     *     "resultMessage" : "success",
     *     "resultData" : [
     *          {
     *              "title" : "민간",
     *              "cnt" : "0"
     *          },
     *          {
     *              "title" : "공공기관",
     *              "cnt" : "2"
     *          },,,,
     *     ]
     * }
     *
     * @param map
     * @return
     */
    private Boolean validateList(Map<String, Object> map) {
        if( map == null || map.isEmpty() ) {
            log.error("K-Startup 기관유형별 사업공고 등록 건수 데이터가 비어있습니다.");
            return false;
        }

        if( !map.get("resultMessage").equals("success") ) {
            log.error("K-Startup 기관유형별 사업공고 등록 건수 API 조회에 실패했습니다.");
            return false;
        }

        if( map.get("resultData") == null ) {
            log.error("K-Startup 기관유형별 사업공고 등록 건수 API 응답 데이터를 확인해주세요.");
            return false;
        }

        List<Map<String, Object>> responselist = (List<Map<String, Object>>) map.get("resultData");
        if( responselist.isEmpty() ) {
            log.error("K-Startup 기관유형별 사업공고 등록 건수 API 응답 데이터를 확인해주세요.");
            return false;
        }

        boolean flag = false;

        for(Map<String, Object> dataMap : responselist) {
            if( dataMap.get("cnt") instanceof Integer ) {
                flag = true;
            }

            if ( dataMap.get("cnt") instanceof String ) {
                try {
                    Integer.parseInt((String) dataMap.get("cnt"));
                    flag = true;
                } catch (NumberFormatException e) {
                    log.error("K-Startup 등록 건수는 숫자 형식이어야 합니다.");
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }
}
