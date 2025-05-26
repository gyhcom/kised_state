package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.KisedorkrCountStatistics;

import java.util.List;

public interface JpaKisedorkrCountStatisticsRepository extends JpaRepository<KisedorkrCountStatistics, Long> {
    List<KisedorkrCountStatistics> findTop30ByOrderByBaseDtAsc();

    KisedorkrCountStatistics findTopByOrderByBaseDt2Desc();
}
