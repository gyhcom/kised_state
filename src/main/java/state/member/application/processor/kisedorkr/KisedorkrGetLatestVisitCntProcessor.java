package state.member.application.processor.kisedorkr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.domain.repository.KisedorkrCountStatisticsRepository;
import state.member.domain.repository.KstupCountStatisticsRepository;

@RequiredArgsConstructor
@Component
public class KisedorkrGetLatestVisitCntProcessor {
    private final KisedorkrCountStatisticsRepository kisedRepo;

    public KisedorkrCountStatistics execute() {
        return kisedRepo.findTopByOrderByBaseDtDesc();
    }
}
