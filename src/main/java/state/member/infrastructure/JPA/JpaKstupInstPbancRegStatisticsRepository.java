package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.KstupInstPbancRegStatistics;

import java.util.List;

public interface JpaKstupInstPbancRegStatisticsRepository extends JpaRepository<KstupInstPbancRegStatistics, Long> {
    List<KstupInstPbancRegStatistics> findAllByOrderByBaseDtAsc();
}
