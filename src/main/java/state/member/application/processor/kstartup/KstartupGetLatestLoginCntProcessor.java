package state.member.application.processor.kstartup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.repository.KstupCountStatisticsRepository;

@RequiredArgsConstructor
@Component
public class KstartupGetLatestLoginCntProcessor {
    private final KstupCountStatisticsRepository kstupRepo;

    public KstupCountStatistics execute() {
        return kstupRepo.findTopByOrderByBaseDtDesc();
    }
}
