package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.KstupCountStatistics;

import java.util.List;

public interface JpaKstupCountStatisticsRepository extends JpaRepository<KstupCountStatistics, Long> {
    List<KstupCountStatistics> findTop30ByOrderByBaseDtAsc();

    KstupCountStatistics findTopByOrderByBaseDtDesc();
}
