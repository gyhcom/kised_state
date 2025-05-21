package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.FdsCntStats;

import java.util.List;

public interface JpaFdsCountStatisticsRepository extends JpaRepository<FdsCntStats, Long> {
    List<FdsCntStats> findTop30ByOrderByBaseDtAsc();
}
