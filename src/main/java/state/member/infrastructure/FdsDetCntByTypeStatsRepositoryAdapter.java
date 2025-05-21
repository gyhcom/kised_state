package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.FdsDetCntByTypeStats;
import state.member.domain.repository.FdsDetCntByTypeStatsRepository;
import state.member.infrastructure.JPA.JpaFdsDetCntByTypeStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FdsDetCntByTypeStatsRepositoryAdapter implements FdsDetCntByTypeStatsRepository {
    private final JpaFdsDetCntByTypeStatsRepository jpaFdsDetCntByTypeStatsRepository;

    @Override
    public List<FdsDetCntByTypeStats> findAllByOrderByBaseDtDesc() {
        return jpaFdsDetCntByTypeStatsRepository.findAllByOrderByBaseDtDesc();
    }

    @Override
    public void saveDetCntByType(List<FdsDetCntByTypeStats> entityList) {
        jpaFdsDetCntByTypeStatsRepository.saveAll(entityList);
    }
}
