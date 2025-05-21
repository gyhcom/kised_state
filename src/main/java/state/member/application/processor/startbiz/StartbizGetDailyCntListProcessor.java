package state.member.application.processor.startbiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.StartbizCountStatistics;
import state.member.domain.repository.StartbizCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StartbizGetDailyCntListProcessor {
    private final StartbizCountStatisticsRepository startbizRepo;

    public List<StartbizCountStatistics> execute() {
        return startbizRepo.findTop30ByOrderByBaseDtAsc();
    }
}
