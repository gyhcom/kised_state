package state.member.application.processor.startbiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.StartbizCountStatistics;
import state.member.domain.repository.StartbizCountStatisticsRepository;

@RequiredArgsConstructor
@Component
public class StartbizGetLatestStatsProcessor {
    private final StartbizCountStatisticsRepository startbizRepo;

    public StartbizCountStatistics execute() {
        return startbizRepo.findTopByOrderByBaseDtDesc();
    }
}
