package state.member.domain.repository;

import state.member.domain.entity.KisedorkrCountStatistics;

import java.util.List;

public interface KisedorkrCountStatisticsRepository {
    void save(KisedorkrCountStatistics entity);
    List<KisedorkrCountStatistics> findTop30ByOrderByBaseDtAsc();

    KisedorkrCountStatistics findTopByOrderByBaseDtDesc();
}
