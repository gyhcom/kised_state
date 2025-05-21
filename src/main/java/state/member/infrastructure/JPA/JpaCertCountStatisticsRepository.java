package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.CertCountStatistics;

import java.util.List;

@Repository
public interface JpaCertCountStatisticsRepository extends JpaRepository<CertCountStatistics, Long> {
    List<CertCountStatistics> findTop30ByOrderByBaseDtAsc();
}
