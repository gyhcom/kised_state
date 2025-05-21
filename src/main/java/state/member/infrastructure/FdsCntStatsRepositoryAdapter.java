package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.repository.FdsCntStatsRepository;
import state.member.infrastructure.JPA.JpaFdsCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FdsCntStatsRepositoryAdapter implements FdsCntStatsRepository {
    private final JpaFdsCountStatisticsRepository jpaFdsCountStatisticsRepository;

    @Override
    public List<FdsCntStats> findTop30ByOrderByBaseDtAsc() {
        return jpaFdsCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }

    @Override
    public void saveDetTotCnt(FdsCntStats entity) {
        jpaFdsCountStatisticsRepository.save(entity);
    }
}
