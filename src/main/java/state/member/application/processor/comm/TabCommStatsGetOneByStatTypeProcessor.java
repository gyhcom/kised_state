package state.member.application.processor.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.TabCommStats;
import state.member.domain.repository.TabCommStatsRepository;

@RequiredArgsConstructor
@Component
public class TabCommStatsGetOneByStatTypeProcessor {
    private final TabCommStatsRepository repo;

    public TabCommStats execute(String statType) {
        return repo.findTopByStatTypeOrderByBaseDtDesc(statType);
    }
}
