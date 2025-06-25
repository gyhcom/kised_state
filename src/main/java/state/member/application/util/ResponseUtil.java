package state.member.application.util;

import lombok.extern.slf4j.Slf4j;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.command.CommResponseCommand;
import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseUtil {
    public static List<TabCommStats> listToEntityNewForm(CommResponseCommand response) {
        if( !validateForm(response) ) {
            log.error("RESPONSE DATA FORM ERROR : {}", response);
            throw new ApiException(ErrorCode.RESPONSE_FORMAT_EXCEPTION);
        }

        // for "baseDt" formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 반복문과 상관없이 동일한 값을 가져오기 때문에 미리 선언
        String version = response.getVersion();
        String sourceSystem = String.valueOf(response.getMeta().get("sourceSystem"));
        String dataType = String.valueOf(response.getMeta().get("dataType"));
        LocalDate baseDt = LocalDate.parse(String.valueOf(response.getMeta().get("date")), formatter);

        List<TabCommStats> resultList = new ArrayList<>();
        for( int i = 0 ; i < response.getData().size() ; i++ ) {
            resultList.add(
                    TabCommStats.builder()
                            .dVersion(version)
                            .sourceSystem(sourceSystem)
                            .dataType(dataType)
                            .baseDt(baseDt)
                            .statType(String.valueOf(response.getData().get(i).get("statType")))
                            .cnt(String.valueOf(response.getData().get(i).get("count")))
                            .regDt(LocalDate.now())
                            .build()
            );
        }

        return resultList;
    }

    /**
     * {
     *     "version" : "1.0",
     *     "meta" : {
     *         "sourceSystem" : "",
     *         "dataType" : "kstup-count",
     *         "date" : 2025-05-21
     *     },
     *     "data" : [
     *         {
     *             "statType" : "kstup_userLginCnt_daily",
     *             "count" : 10
     *         },
     *         {
     *             "statType" : "kstup_userLginCnt_monthly",
     *             "count" : 50
     *         },
     *         {
     *             "statType" : "kstup_userLginCnt_yearly",
     *             "count" : 100
     *         },,,
     *
     *         * 추가 항목에 대해서 추가 가능
     *     ]
     * }
     * @param response
     * @return
     */
    private static Boolean validateForm(CommResponseCommand response) {
        if( response == null ) return false;

        /* version 체크 시작 */
        if( response.getVersion() == null || response.getVersion().isBlank() ) return false;
        /* version 체크 종료 */

        /* meta 체크 시작 */
        if( response.getMeta() == null || response.getMeta().isEmpty() ) return false;

        if( response.getMeta().getOrDefault("sourceSystem", "N").equals("N") ) return false;
        if( String.valueOf(response.getMeta().get("sourceSystem")).isBlank() ) return false;

        if( response.getMeta().getOrDefault("dataType", "N").equals("N") ) return false;
        if( String.valueOf(response.getMeta().get("dataType")).isBlank() ) return false;

        if( response.getMeta().getOrDefault("date", "N").equals("N") ) return false;
        if( String.valueOf(response.getMeta().get("date")).isBlank() ) return false;
        /* meta 체크 종료 */

        /* data 체크 시작 */
        if( response.getData() == null || response.getData().isEmpty() ) return false;

        for( Map<String, Object> map : response.getData() ) {
            if( map.getOrDefault("statType", "N").equals("N") ) return false;
            if( String.valueOf(map.get("statType")).isBlank() ) return false;

            if( map.getOrDefault("count", "N").equals("N") ) return false;

            // 건수 체크 -> 정수로 변환 가능한 값인지 체크
            try {
                Integer.parseInt(String.valueOf(map.get("count")));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        /* data 체크 종료 */

        return true;
    }
}
