package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.domain.repository.KisedorkrCountStatisticsRepository;
import state.member.infrastructure.JPA.JpaKisedorkrCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class KisedorkrCountStatisticsRepositoryAdapter implements KisedorkrCountStatisticsRepository {
    private final JpaKisedorkrCountStatisticsRepository jpaKisedorkrCountStatisticsRepository;

    @Override
    public void save(KisedorkrCountStatistics entity) {
        jpaKisedorkrCountStatisticsRepository.save(entity);
    }

    @Override
    public List<KisedorkrCountStatistics> findTop30ByOrderByBaseDtAsc() {
        return jpaKisedorkrCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }

    @Override
    public KisedorkrCountStatistics findTopByOrderByBaseDtDesc() {
        return jpaKisedorkrCountStatisticsRepository.findTopByOrderByBaseDt2Desc();
    }
}
