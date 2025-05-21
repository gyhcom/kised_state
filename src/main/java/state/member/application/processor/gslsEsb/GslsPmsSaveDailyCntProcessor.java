package state.member.application.processor.gslsEsb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.GslsPmsCountStatistics;
import state.member.domain.repository.GslsPmsCountStatisticsRepository;

/**
 * 국고보조금(PMS) 통계 일일 적재 배치
 */
@RequiredArgsConstructor
@Component
public class GslsPmsSaveDailyCntProcessor {
    private final GslsPmsCountStatisticsRepository gslsPmsRepo;

    public void execute(GslsPmsCountStatistics entity) {
        gslsPmsRepo.save(entity);
    }
}
