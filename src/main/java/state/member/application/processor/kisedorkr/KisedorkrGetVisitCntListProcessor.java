package state.member.application.processor.kisedorkr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KisedorkrCountStatistics;
import state.member.domain.repository.KisedorkrCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KisedorkrGetVisitCntListProcessor {
    private final KisedorkrCountStatisticsRepository kisedRepo;

    public List<KisedorkrCountStatistics> execute() {
        return kisedRepo.findTop30ByOrderByBaseDtAsc();
    }
}
