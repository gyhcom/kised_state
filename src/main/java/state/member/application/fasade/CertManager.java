package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.processor.cert.CertCntProcessor;
import state.member.application.processor.cert.CertGetCntListProcessor;
import state.member.application.processor.cert.CertSaveCntProcessor;
import state.member.domain.entity.CertCountStatistics;

import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class CertManager {
    private final CertCntProcessor certCntProcessor;
    private final CertSaveCntProcessor certSaveCntProcessor;
    private final CertGetCntListProcessor certGetCntListProcessor;

    public Mono<Map<String, Object>> getCertCnt() {
        return certCntProcessor.execute();
    }

    public void saveCertCnt(CertCountStatistics cert) {
        certSaveCntProcessor.execute(cert);
    };

    // TODO Command 만들어서 Entity 대신 Command로 핸들링하기
    public List<CertCountStatistics> getCertDailyList() {
        return certGetCntListProcessor.execute();
    }

    public CertCountStatistics mapToEntity(Map<String, Object> map) {
        if( !validateMap(map) ) {
            throw new ApiException(ErrorCode.NULL_POINT, "창업기업확인 통계 데이터를 확인해주세요.");
        }

        Map<String, Object> statsMap = (Map<String, Object>) map.get("stats");

        return CertCountStatistics.builder()
                .confmDocAplyCnt(String.valueOf(statsMap.get("confmDocAplyCnt")))
                .confmDocIssuCnt(String.valueOf(statsMap.get("confmDocIssuCnt")))
                .confmDocRjctCnt(String.valueOf(statsMap.get("confmDocRjctCnt")))
                .build();
    }

    /**
     * 창업기업확인 적재 데이터 체크
     * {
     *     "systmNm" : "CERT",
     *     "date" : "20250325(예시)"
     *     "stats" : {
     *         "confmDocAplyCnt" : 55,
     *         "confmDocIssuCnt" : 56,
     *         "confmDocRjctCnt" : 57
     *     }
     * }
     * @param map
     * @return
     */
    private Boolean validateMap(Map<String, Object> map) {
        // stats 자체가 없을 경우
        if( map.getOrDefault("stats", "N").equals("N") ) {
            return false;
            //throw new ApiException(ErrorCode.NULL_POINT, "창업기업확인 통계 데이터가 비어있습니다.");
        }

        // stats는 있지만 "정수 형식" 값 체크
        Map<String, Object> stats = (Map<String, Object>) map.get("stats");
        if( stats.get("confmDocAplyCnt") instanceof Integer &&
                stats.get("confmDocIssuCnt") instanceof Integer &&
                stats.get("confmDocRjctCnt") instanceof Integer ) {
            return true;
        }

        if ( stats.get("confmDocAplyCnt") instanceof String ) {
            try {
                Integer.parseInt((String) stats.get("confmDocAplyCnt"));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if ( stats.get("confmDocIssuCnt") instanceof String ) {
            try {
                Integer.parseInt((String) stats.get("confmDocIssuCnt"));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if ( stats.get("confmDocRjctCnt") instanceof String ) {
            try {
                Integer.parseInt((String) stats.get("confmDocRjctCnt"));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return false;
    }
}
