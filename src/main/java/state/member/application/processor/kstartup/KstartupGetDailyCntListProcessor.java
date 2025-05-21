package state.member.application.processor.kstartup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.repository.KstupCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KstartupGetDailyCntListProcessor {
    private final KstupCountStatisticsRepository kstupRepo;

    public List<KstupCountStatistics> execute() {
        return kstupRepo.findTop30ByOrderByBaseDtAsc();
    }
}
