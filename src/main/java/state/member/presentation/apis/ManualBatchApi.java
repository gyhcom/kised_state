package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import state.member.application.comm.StatTypes;
import state.member.application.command.CommResponseCommand;
import state.member.application.fasade.*;
import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manualBatch")
@RestController
public class ManualBatchApi {
    /**
     * 수동 배치 한 곳에 몰아 넣은 이유
     * 1. Security 예외 시키기에 적절하다고 판단.
     * 2. 각 서비스 별 controller에 반영 시, 동일한 의존성이 모두 추가되어야 함. -> JobLauncher 반복 선언
     * 3. 그래서 수동 배치는 한 곳에서 관리하는 것이 좋을 것 같다고 판단
     */
    private final KstupManager kstupManager;
    private final StartbizManager startbizManager;
    private final FdsManager fdsManager;
    private final GslsEsbManager gslsEsbManager;
    private final KisedorkrManager kisedorkrManager;
    private final TabCommStatsManager commManager;

    private final JobLauncher jobLauncher;

    @Qualifier("certCntJob")
    private final Job certCntJob;

    @Qualifier("fdsTotDetCntDailyJob")
    private final Job fdsTotDetCntDailyJob;

    @Qualifier("fdsDetCntByTypeDailyJob")
    private final Job fdsDetCntByTypeDailyJob;

    @Qualifier("fdsTotDetCntByDateJob")
    private final Job fdsTotDetCntByDateJob;

    @Qualifier("gslsPmsAllStatDailyJob")
    private final Job gslsPmsAllStatDailyJob;

    @Qualifier("gslsPmsAllStatsByDateJob")
    private final Job gslsPmsAllStatsByDateJob;

    @Qualifier("kisedorkrDailyCntJob")
    private final Job kisedorkrDailyCntJob;

    @Qualifier("kstupDailyCntJob")
    private final Job kstupDailyCntJob;

    @Qualifier("kstupDailyListJob")
    private final Job kstupDailyListJob;

    @Qualifier("startbizDailyCntJob")
    private final Job startbizDailyCntJob;

    @GetMapping("/cert/manualBatch.do")
    public void runCertCntBatchJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(certCntJob, jobParameters);
        } catch (Exception e) {
            log.error("창업기업확인 certCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/fds/totCntDaily")
    public void runFdsTotCntDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("fdsTotDetCntDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(fdsTotDetCntDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("사업비점검 runFdsTotDetCntDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/fds/totDetCntByTypeDaily")
    public void runFdsDetCntByTypeDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("runFdsDetCntByTypeDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(fdsDetCntByTypeDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("사업비점검 runFdsDetCntByTypeDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/fds/totDetCntByDate")
    public void runFdsTotDetCntByDateJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("runFdsTotDetCntByDateJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(fdsTotDetCntByDateJob, jobParameters);
        } catch (Exception e) {
            log.error("사업비점검 runFdsDetCntByTypeDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/gslsPms/allStatDaily")
    public void runGslsPmsAllStatDailyJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("gslsPmsAllStatDailyJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(gslsPmsAllStatDailyJob, jobParameters);
        } catch (Exception e) {
            log.error("국고보조금(PMS) runGslsPmsAllStatDailyJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/gslsPms/allStatsByDate")
    public void runGslsPmsAllStatsByDateJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("runGslsPmsAllStatsByDateJobTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(gslsPmsAllStatsByDateJob, jobParameters);
        } catch (Exception e) {
            log.error("국고보조금(PMS) runGslsPmsAllStatsByDateJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/kisedorkr/dailyCnt")
    public void runKisedDailyCntJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(kisedorkrDailyCntJob, jobParameters);
        } catch (Exception e) {
            log.error("기관 홈페이지 kisedorkrDailyCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/kstup/dailyCnt")
    public void runDailyCntBatchJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(kstupDailyCntJob, jobParameters);
        } catch (Exception e) {
            log.error("K-Startup kstupDailyCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/kstup/dailyList")
    public void runKstupDailyListJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(kstupDailyListJob, jobParameters);
        } catch(Exception e) {
            log.error("K-Startup kstupDailyListJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }

    }

    @GetMapping("/startbiz/dailyCnt")
    public void runStartbizDailyCntJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(startbizDailyCntJob, jobParameters);
        } catch (Exception e) {
            log.error("법인설립시스템 startbizDailyCntJob 배치 실행 중 예외 발생 : {}", e.getMessage());
        }
    }

    @GetMapping("/setDummData")
    public String setDummData() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Map<String, Object> tempMap = new HashMap<>();

        for( int i = 0 ; i < 30 ; i++ ) {
            int ranLowVal = random.nextInt(51) + 50;
            int ranMidVal = random.nextInt(101) + 100;
            int ranHighVal = random.nextInt(201) + 100;
            int ranMegaVal = random.nextInt(1001) + 100;
            LocalDate date = LocalDate.parse("202505"+convertDayForm(i+1), formatter);

            // =============법인설립시스템 더미데이터 적재 시작================
            Map<String, Object> startbizDataMap = new HashMap<>();
            startbizDataMap.put("corpFndnCnt", ranLowVal);
            startbizDataMap.put("vstCnt", ranMidVal);
            startbizDataMap.put("baseDt2", date);
            tempMap.put("stats", startbizDataMap);

            //저장 로직 실행
            startbizManager.saveDailyCnt(startbizManager.mapToEntity(tempMap));
            // =============법인설립시스템 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============사업비점검 총 이상거래 탐지 건수 더미데이터 적재 시작================
            Map<String, Object> fdsDataMap = new HashMap<>();
            fdsDataMap.put("CNT", ranLowVal);
            fdsDataMap.put("baseDt2", date);
            tempMap.put("resultData", fdsDataMap);

            //저장 로직 실행
            fdsManager.saveTotCnt(fdsManager.mapToEntity(tempMap));
            // =============사업비점검 총 이상거래 탐지 건수 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============국고보조금(PMS) 더미데이터 적재 시작================
            Map<String, Object> gslsDataMap = new HashMap<>();
            gslsDataMap.put("excutCnt", ranHighVal);
            gslsDataMap.put("ifpbntCnt", ranLowVal);
            gslsDataMap.put("anlrveCnt", ranLowVal);
            gslsDataMap.put("fnlttCnt", ranLowVal);
            gslsDataMap.put("ddtlbzCnt", ranMegaVal);
            gslsDataMap.put("baseDt2", date);
            tempMap.put("map", gslsDataMap);

            //저장 로직 실행
            gslsEsbManager.saveDailyStat(gslsEsbManager.mapToEntity(tempMap));
            // =============국고보조금(PMS) 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============K-Startup 단건 더미데이터 적재 시작================
            Map<String, Object> lginMap = new HashMap<>();
            Map<String, Object> intgPbancRegMap = new HashMap<>();
            Map<String, Object> bizPbancRegInstMap = new HashMap<>();
            Map<String, Object> mnpwMap = new HashMap<>();

            lginMap.put("lginCnt", ranMegaVal);
            lginMap.put("lginDuplCnt", ranHighVal);
            intgPbancRegMap.put("cnt", ranLowVal);
            bizPbancRegInstMap.put("cnt", ranMidVal);
            mnpwMap.put("cnt", random.nextInt(11) + 100);
            mnpwMap.put("baseDt2", date);

            tempMap.put("lginCnt", lginMap);
            tempMap.put("intgPbancRegCnt", intgPbancRegMap);
            tempMap.put("bizPbancRegInstCnt", bizPbancRegInstMap);
            tempMap.put("mnpwCnt", mnpwMap);

            //저장 로직 실행
            kstupManager.saveDailyCnt(kstupManager.mapToEntity(tempMap));
            // =============K-Startup 단건 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============기관 홈페이지 더미데이터 적재 시작================
            Map<String, Object> kisedorkrMap = new HashMap<>();
            kisedorkrMap.put("cnt", ranHighVal);
            kisedorkrMap.put("baseDt2", date);
            tempMap.put("resultData", kisedorkrMap);

            //저장 로직 실행
            kisedorkrManager.saveVisitCnt(kisedorkrManager.mapToEntity(tempMap));
            // =============기관 홈페이지 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============K-Startup 기관유형별 공고등록 건수 더미데이터 적재 시작================
            Map<String, Object> kstupListMap = new HashMap<>();
            List<Map<String, Object>> dataList = new ArrayList<>();
            kstupListMap.put("title", "민간");
            kstupListMap.put("cnt", ranLowVal);
            kstupListMap.put("baseDt2", date);

            dataList.add(kstupListMap);

            Map<String, Object> kstupListMap2 = new HashMap<>();
            kstupListMap2.put("title", "공공기관");
            kstupListMap2.put("cnt", ranHighVal);
            kstupListMap2.put("baseDt2", date);

            dataList.add(kstupListMap2);

            Map<String, Object> kstupListMap3 = new HashMap<>();
            kstupListMap3.put("title", "지자체");
            kstupListMap3.put("cnt", ranMidVal);
            kstupListMap3.put("baseDt2", date);

            dataList.add(kstupListMap3);

            Map<String, Object> kstupListMap4 = new HashMap<>();
            kstupListMap4.put("title", "교육기관");
            kstupListMap4.put("cnt", ranMegaVal);
            kstupListMap4.put("baseDt2", date);

            dataList.add(kstupListMap4);

            tempMap.put("resultData", dataList);

            //저장 로직 실행
            kstupManager.saveBizPbancRegCntList(kstupManager.listToEntity(tempMap));
            // =============K-Startup 기관유형별 공고등록 건수 더미데이터 적재 종료================

            tempMap.clear(); /* 초기화 */

            // =============사업비점검 유형별 탐지 건수 더미데이터 적재 시작================
            String[] fieldsList = {
                    "집중 거래",
                    "과다 집행",
                    "사업비 편취",
                    "특수관계 거래",
                    "협약 변경",
                    "인건비",
                    "비정상 거래",
                    "사업비 부적정"
            };

            // 소관부처 더미 데이터
            String[] cowCrownBuddhaList = {
                    "특정 거래처와 다수 수혜기업 거래",
                    "협약 기간 중 사업계획 과다변경 탐지",
                    "1회 사업비 과다 집행(금액)",
                    "부가가치세를 포함한 사업비 진행",
                    "임직원에 대한 인건비 과다집행 (금액)",
                    "특정거래대상과 수혜기업 간 집중거래(사업비카드)"
            };

            // 사업명 더미 데이터
            String[] bsnsNmList = {
                    "DS001",
                    "DS002",
                    "DS005",
                    "DS007",
                    "DS0012",
                    "DS0017",
                    "DS0018",
                    "DS0013",
                    "DS0020",
                    "DS0029",
                    "DS0023",
                    "DS0025",
                    "DS0033",
                    "DS0036",
                    "DS0051",
            };

            List<Map<String, Object>> dummyList = new ArrayList<>();

            //String fieldIdx = String.valueOf(random.nextInt(8) + 1);

            // 탐지 구분명 랜덤 선택
            String field = fieldsList[random.nextInt(fieldsList.length)];

            // 탐지유형명 랜덤 선택
            String cowCrownBuddha = cowCrownBuddhaList[random.nextInt(cowCrownBuddhaList.length)];

            //탐지유형 코드 랜덤 선택
            String bsnsNm = bsnsNmList[random.nextInt(bsnsNmList.length)];

            Map<String, Object> dummMap = new HashMap<>();
            dummMap.put("DET_TP_CD", bsnsNm);
            dummMap.put("DET_TP_NM", cowCrownBuddha);
            dummMap.put("DET_SE_NM", field);
            dummMap.put("CNT", ranLowVal);
            dummMap.put("baseDt2", date);

            dummyList.add(dummMap);

            tempMap.put("resultData", dummyList);

            fdsManager.saveDetCntByType(fdsManager.listToEntity(tempMap));
            // =============사업비점검 유형별 탐지 건수 더미데이터 적재 종료================
        }

        return "success";
    }

    @GetMapping("/initData")
    public void initData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Map<String, Object>> monthlyList = Arrays.stream(StatTypes.values())
                                            .filter(systemName -> systemName.getStatType().toUpperCase().contains("MONTHLY"))
                                            .map(statTypes -> {
                                                Map<String, Object> resultMap = new HashMap<>();
                                                resultMap.put("statType", statTypes.getStatType());
                                                return resultMap;
                                            })
                                            .toList();

        List<TabCommStats> resultList = new ArrayList<>();
        for( Map<String, Object> map : monthlyList ) {
            // 월 데이터 세팅
            for( int i = 1 ; i <= 5 ; i++ ) {
                resultList.add(
                    TabCommStats.builder()
                            .dVersion("1.0")
                            .sourceSystem("TEST")
                            .dataType("DUMMY")
                            .baseDt(LocalDate.parse("2025-0" + i + "-01", formatter))
                            .statType(String.valueOf(map.get("statType")))
                            .cnt(String.valueOf(100+(i*10)))
                            .regDt(LocalDate.now())
                            .build()
                );
            }
        }

        List<Map<String, Object>> yearlyList = Arrays.stream(StatTypes.values())
                .filter(systemName -> systemName.getStatType().toUpperCase().contains("YEARLY"))
                .map(statTypes -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("statType", statTypes.getStatType());
                    return resultMap;
                })
                .toList();

        for( Map<String, Object> map : yearlyList ) {
            // 연 데이터 세팅
            for( int i = 3 ; i <= 5 ; i++ ) {
                resultList.add(
                        TabCommStats.builder()
                                .dVersion("1.0")
                                .sourceSystem("TEST")
                                .dataType("DUMMY")
                                .baseDt(LocalDate.parse("202" + i + "-12-31", formatter))
                                .statType(String.valueOf(map.get("statType")))
                                .cnt(String.valueOf(100+(i*100)))
                                .regDt(LocalDate.now())
                                .build()
                );
            }
        }

        List<Map<String, Object>> dailyList = Arrays.stream(StatTypes.values())
                .filter(systemName -> systemName.getStatType().toUpperCase().contains("DAILY"))
                .map(statTypes -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("statType", statTypes.getStatType());
                    return resultMap;
                })
                .toList();

        for( Map<String, Object> map : dailyList ) {
            // 일 데이터 세팅
            for( int i = 1 ; i <= 30 ; i++ ) {
                resultList.add(
                        TabCommStats.builder()
                                .dVersion("1.0")
                                .sourceSystem("TEST")
                                .dataType("DUMMY")
                                .baseDt(LocalDate.parse("2025-06-"+convertDayForm(i), formatter))
                                .statType(String.valueOf(map.get("statType")))
                                .cnt(String.valueOf(10+i))
                                .regDt(LocalDate.now())
                                .build()
                );
            }
        }

        commManager.saveAllStats(resultList);
    }

    private String convertDayForm(int i) {
        return (i < 10) ? "0"+i : ""+i;
    }
}
