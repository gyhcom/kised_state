package state.member.domain.repository;

import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.entity.KstupInstPbancRegStatistics;

import java.util.List;

public interface KstupInstPbancRegStatisticsRepository {
    void saveAll(List<KstupInstPbancRegStatistics> entityList);

    List<KstupInstPbancRegStatistics> findAllByOrderByBaseDtAsc();
}
