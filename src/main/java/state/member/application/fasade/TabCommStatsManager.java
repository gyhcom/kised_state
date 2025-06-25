package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.processor.comm.*;
import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TabCommStatsManager {
    private final TabCommStatsSaveProcessor tabCommStatsSaveProcessor;
    private final TabCommStatsGetTop30ByStatTypeProcessor tabCommStatsGetTop30ByStatTypeProcessor;
    private final TabCommStatsGetOneByStatTypeProcessor tabCommStatsGetOneByStatTypeProcessor;
    private final TabCommStatsGetStatsByPeriodProcessor tabCommStatsGetStatsByPeriodProcessor;
    private final TabCommStatsGetMonthlyByStatTypeProcessor tabCommStatsGetMonthlyByStatTypeProcessor;
    private final TabCommStatsGetYearlyByStatTypeProcessor tabCommStatsGetYearlyByStatTypeProcessor;

    private final TabCommStatsGetAllProcessor tabCommStatsGetAllProcessor;

    public void saveAllStats(List<TabCommStats> entityList) {
        tabCommStatsSaveProcessor.execute(entityList);
    }

    public List<TabCommStats> getStatsTop30(String statType) {
        return tabCommStatsGetTop30ByStatTypeProcessor.execute(statType);
    }

    public TabCommStats getOne(String statType) {
        return tabCommStatsGetOneByStatTypeProcessor.execute(statType);
    }

    public List<TabCommStats> getAll() {
        return tabCommStatsGetAllProcessor.execute();
    }

    public List<TabCommStats> getStatsListByPeriod(String statType, LocalDate startDate, LocalDate endDate) {
        return tabCommStatsGetStatsByPeriodProcessor.execute(statType, startDate, endDate);
    }

    public List<TabCommStats> getMonthlyStatsList(String statType) {
        return tabCommStatsGetMonthlyByStatTypeProcessor.execute(statType);
    }

    public List<TabCommStats> getYearlyStatsList(String statType) {
        return tabCommStatsGetYearlyByStatTypeProcessor.execute(statType);
    }
}
