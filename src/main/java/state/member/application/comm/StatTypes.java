package state.member.application.comm;

import lombok.Getter;

/**
 * "statType"엔 실제 DB에 적재되는 statType이 정의되어 있어야 한다.
 * ex) 국고보조금(PMS) - EXCUT_CNT_DAILY -> excutCntDaily
 */
@Getter
public enum StatTypes {
    KISEDORKR_VISITOR("방문자수", "kisedorkrVisitorDaily", "kisedorkr"),

    /* 국고보조금(PMS) 시작 */
    GSLSPMS_EXCUT_CNT_DAILY("수급자집행정보 건수", "excutCntDaily", "gslsPms"),
    GSLSPMS_IFPBNT_CNT_DAILY("정보공시내역 건수", "ifpbntCntDaily", "gslsPms"),
    GSLSPMS_ANLRVE_CNT_DAILY("세입세출내역 건수", "anlrveCntDaily", "gslsPms"),
    GSLSPMS_FNLTT_CNT_DAILY("재무제표결산서 건수", "fnlttCntDaily", "gslsPms"),
    GSLSPMS_DDTLBZ_CNT_DAILY("상세내역사업정보 건수", "ddtlbzCntDaily", "gslsPms"),
    GSLSPMS_EXCUT_CNT_MONTHLY("수급자집행정보 건수(월별)", "excutCntMonthly", "gslsPms"),
    GSLSPMS_DDTLBZ_CNT_YEARLY("상세내역사업정보 건수(연도별)", "ddtlbzCntYearly", "gslsPms"),
    /* 국고보조금(PMS) 종료 */

    /* 사업비점검 시작 */
    FDS_TOT_CNT_DAILY("총 이상거래탐지 건수", "FDS_TOT_CNT_DAILY", "fds"),
    FDS_TOT_CNT_MONTHLY("총 이상거래탐지 건수(월별)", "FDS_TOT_CNT_MONTHLY", "fds"),
    FDS_TOT_CNT_YEARLY("총 이상거래탐지 건수(연도별)", "FDS_TOT_CNT_YEARLY", "fds"),
    /* 사업비점검 종료 */

    /* 법인설립시스템 시작 */
    STARTBIZ_CORP_FNDN_CNT_DAILY("법인설립 건수", "corpFndnCnt_daily", "startbiz"),
    STARTBIZ_CORP_FNDN_CNT_MONTHLY("법인설립 건수(월별)", "corpFndnCnt_monthly", "startbiz"),
    STARTBIZ_CORP_FNDN_CNT_YEARLY("법인설립 건수(연도별)", "corpFndnCnt_yearly", "startbiz"),
    STARTBIZ_VISIT_DAILY("방문자 수", "vstCntDay_daily", "startbiz"),
    STARTBIZ_VISIT_MONTHLY("방문자 수(월별)", "vstCnt_monthly", "startbiz"),
    STARTBIZ_VISIT_YEARLY("방문자 수(연도별)", "vstCnt_yearly", "startbiz");
    /* 법인설립시스템 종료 */

    private final String typeNm;
    private final String statType;
    private final String systemNm;

    StatTypes(String typeNm, String statType, String systemNm) {
        this.typeNm = typeNm;
        this.statType = statType;
        this.systemNm = systemNm;
    }
}
