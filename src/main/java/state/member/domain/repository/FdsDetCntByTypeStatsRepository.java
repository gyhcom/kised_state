package state.member.domain.repository;

import state.member.domain.entity.FdsDetCntByTypeStats;

import java.util.List;

public interface FdsDetCntByTypeStatsRepository {
    List<FdsDetCntByTypeStats> findAllByOrderByBaseDtDesc();

    void saveDetCntByType(List<FdsDetCntByTypeStats> entityList);
}
