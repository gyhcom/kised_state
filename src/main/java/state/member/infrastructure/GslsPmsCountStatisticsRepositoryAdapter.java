package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.GslsPmsCountStatistics;
import state.member.domain.repository.GslsPmsCountStatisticsRepository;
import state.member.infrastructure.JPA.JpaGslsPmsCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GslsPmsCountStatisticsRepositoryAdapter implements GslsPmsCountStatisticsRepository {
    private final JpaGslsPmsCountStatisticsRepository jpaGslsPmsCountStatisticsRepository;

    @Override
    public List<GslsPmsCountStatistics> findTop30ByOrderByBaseDtAsc() {
        return jpaGslsPmsCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }

    @Override
    public GslsPmsCountStatistics findTopByOrderByBaseDtDesc() {
        return jpaGslsPmsCountStatisticsRepository.findTopByOrderByBaseDt2Desc();
    }

    @Override
    public void save(GslsPmsCountStatistics entity) {
        jpaGslsPmsCountStatisticsRepository.save(entity);
    }
}
