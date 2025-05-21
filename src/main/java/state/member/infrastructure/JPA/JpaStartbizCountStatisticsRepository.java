package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.StartbizCountStatistics;

import java.util.List;

public interface JpaStartbizCountStatisticsRepository extends JpaRepository<StartbizCountStatistics, Long> {
    List<StartbizCountStatistics> findTop30ByOrderByBaseDtAsc();
}
