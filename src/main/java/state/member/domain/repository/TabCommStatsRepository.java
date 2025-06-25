package state.member.domain.repository;

import state.member.domain.entity.TabCommStats;

import java.time.LocalDate;
import java.util.List;

public interface TabCommStatsRepository {
    List<TabCommStats> getStats30ByStatType(String statType);

    TabCommStats findTopByStatTypeOrderByBaseDtDesc(String statType);

    List<TabCommStats> findAll();

    void saveAll(List<TabCommStats> tabCommStatsList);

    List<TabCommStats> findByStatTypeAndBaseDtBetween(String statType, LocalDate startDate, LocalDate endDate);

    List<TabCommStats> findByStatTypeMonthly(String statType);

    List<TabCommStats> findByStatTypeYearly(String statType);
}
