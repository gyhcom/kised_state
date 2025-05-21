package state.member.application.processor.kstartup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KstupCountStatistics;
import state.member.domain.repository.KstupCountStatisticsRepository;

@RequiredArgsConstructor
@Component
public class KstartupSaveDailyCntProcessor {
    private final KstupCountStatisticsRepository kstupRepo;

    public void execute(KstupCountStatistics entity) {
        kstupRepo.save(entity);
    }
}
