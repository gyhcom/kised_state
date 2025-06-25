package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.TabCommStats;
import state.member.domain.repository.TabCommStatsRepository;
import state.member.infrastructure.JPA.JpaTabCommStatsRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TabCommStatsRepositoryAdapter implements TabCommStatsRepository {
    private final JpaTabCommStatsRepository jpa;
    @Override
    public List<TabCommStats> getStats30ByStatType(String statType) {
        return jpa.findTop30ByStatTypeOrderByBaseDtAsc(statType);
    }

    @Override
    public TabCommStats findTopByStatTypeOrderByBaseDtDesc(String statType) {
        return jpa.findTopByStatTypeOrderByBaseDtDesc(statType);
    }

    @Override
    public List<TabCommStats> findAll() {
        return jpa.findAll();
    }

    @Override
    public void saveAll(List<TabCommStats> tabCommStatsList) {
        jpa.saveAll(tabCommStatsList);
    }

    @Override
    public List<TabCommStats> findByStatTypeAndBaseDtBetween(String statType, LocalDate startDate, LocalDate endDate) {
        return jpa.findByStatTypeAndBaseDtBetweenOrderByBaseDtDesc(statType, startDate, endDate);
    }

    @Override
    public List<TabCommStats> findByStatTypeMonthly(String statType) {
        return jpa.findByStatTypeMonthly(statType);
    }

    @Override
    public List<TabCommStats> findByStatTypeYearly(String statType) {
        return jpa.findByStatTypeYearly(statType);
    }
}
