package state.member.domain.repository;

import state.member.domain.entity.KstupCountStatistics;

import java.util.List;

public interface KstupCountStatisticsRepository {
    void save(KstupCountStatistics entity);
    List<KstupCountStatistics> findTop30ByOrderByBaseDtAsc();

    KstupCountStatistics findTopByOrderByBaseDtDesc();
}
