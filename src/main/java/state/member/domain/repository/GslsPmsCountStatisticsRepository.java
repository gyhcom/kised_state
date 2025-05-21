package state.member.domain.repository;

import state.member.domain.entity.GslsPmsCountStatistics;

import java.util.List;

public interface GslsPmsCountStatisticsRepository {
    List<GslsPmsCountStatistics> findTop30ByOrderByBaseDtAsc();

    GslsPmsCountStatistics findTopByOrderByBaseDtDesc();

    void save(GslsPmsCountStatistics entity);
}
