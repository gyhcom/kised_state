package state.member.application.processor.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.TabCommStats;
import state.member.domain.repository.TabCommStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TabCommStatsGetTop30ByStatTypeProcessor {
    private final TabCommStatsRepository repo;

    public List<TabCommStats> execute(String statType) {
        return repo.getStats30ByStatType(statType);
    }
}
