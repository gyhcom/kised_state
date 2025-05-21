package state.member.domain.repository;

import state.member.domain.entity.CertCountStatistics;

import java.util.List;

public interface CertCountStatisticsRepository {
    void save(CertCountStatistics cert);

    List<CertCountStatistics> findTop30ByOrderByBaseDtAsc();
}
