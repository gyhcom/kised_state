package state.member.application.processor.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.TabCommStats;
import state.member.domain.repository.TabCommStatsRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TabCommStatsGetStatsByPeriodProcessor {
    private final TabCommStatsRepository repo;

    public List<TabCommStats> execute(String statType, LocalDate startDate, LocalDate endDate) {
        return repo.findByStatTypeAndBaseDtBetween(statType, startDate, endDate);
    }
}
