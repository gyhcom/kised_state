package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.repository.KstupCountStatisticsRepository;
import state.member.infrastructure.JPA.JpaKstupCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class KstupCountStatisticsRepositoryAdapter implements KstupCountStatisticsRepository {
    private final JpaKstupCountStatisticsRepository jpaKstupCountStatisticsRepository;

    @Override
    public void save(KstupCountStatistics entity) {
        jpaKstupCountStatisticsRepository.save(entity);
    }

    @Override
    public List<KstupCountStatistics> findTop30ByOrderByBaseDtAsc() {
        return jpaKstupCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }

    @Override
    public KstupCountStatistics findTopByOrderByBaseDtDesc() {
        return jpaKstupCountStatisticsRepository.findTopByOrderByBaseDt2Desc();
    }
}
