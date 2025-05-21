package state.member.domain.repository;

import state.member.domain.entity.FdsCntStats;

import java.util.List;

public interface FdsCntStatsRepository {
    List<FdsCntStats> findTop30ByOrderByBaseDtAsc();

    void saveDetTotCnt(FdsCntStats entity);
}
