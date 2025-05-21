package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.FdsDetCntByTypeStats;

import java.util.List;

public interface JpaFdsDetCntByTypeStatsRepository extends JpaRepository<FdsDetCntByTypeStats, Long> {
    List<FdsDetCntByTypeStats> findAllByOrderByBaseDtDesc();
}
