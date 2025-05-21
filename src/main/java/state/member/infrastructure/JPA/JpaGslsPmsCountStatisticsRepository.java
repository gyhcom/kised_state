package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.GslsPmsCountStatistics;

import java.util.List;

public interface JpaGslsPmsCountStatisticsRepository extends JpaRepository<GslsPmsCountStatistics, Long> {
    List<GslsPmsCountStatistics> findTop30ByOrderByBaseDtAsc();

    GslsPmsCountStatistics findTopByOrderByBaseDtDesc();
}
