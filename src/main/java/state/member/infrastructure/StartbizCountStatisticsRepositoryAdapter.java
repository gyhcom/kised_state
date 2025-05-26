package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.StartbizCountStatistics;
import state.member.domain.repository.StartbizCountStatisticsRepository;
import state.member.infrastructure.JPA.JpaStartbizCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class StartbizCountStatisticsRepositoryAdapter implements StartbizCountStatisticsRepository {
    private final JpaStartbizCountStatisticsRepository jpaStartbizCountStatisticsRepository;

    @Override
    public void save(StartbizCountStatistics entity) {
        jpaStartbizCountStatisticsRepository.save(entity);
    }

    @Override
    public List<StartbizCountStatistics> findTop30ByOrderByBaseDtAsc() {
        return jpaStartbizCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }

    @Override
    public StartbizCountStatistics findTopByOrderByBaseDtDesc() {
        return jpaStartbizCountStatisticsRepository.findTopByOrderByBaseDt2Desc();
    }
}
