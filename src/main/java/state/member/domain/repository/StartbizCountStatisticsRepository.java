package state.member.domain.repository;

import state.member.domain.entity.StartbizCountStatistics;

import java.util.List;

public interface StartbizCountStatisticsRepository {
    void save(StartbizCountStatistics entity);

    List<StartbizCountStatistics> findTop30ByOrderByBaseDtAsc();

    StartbizCountStatistics findTopByOrderByBaseDtDesc();
}
