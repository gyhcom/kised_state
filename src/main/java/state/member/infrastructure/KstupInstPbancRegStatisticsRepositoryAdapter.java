package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.KstupInstPbancRegStatistics;
import state.member.domain.repository.KstupInstPbancRegStatisticsRepository;
import state.member.infrastructure.JPA.JpaKstupInstPbancRegStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class KstupInstPbancRegStatisticsRepositoryAdapter implements KstupInstPbancRegStatisticsRepository {
    private final JpaKstupInstPbancRegStatisticsRepository jpaKstupInstPbancRegStatisticsRepository;

    @Override
    public void saveAll(List<KstupInstPbancRegStatistics> entityList) {
        jpaKstupInstPbancRegStatisticsRepository.saveAll(entityList);
    }

    @Override
    public List<KstupInstPbancRegStatistics> findAllByOrderByBaseDtAsc() {
        return jpaKstupInstPbancRegStatisticsRepository.findAllByOrderByBaseDtAsc();
    }
}
