package state.member.application.processor.kstartup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KstupInstPbancRegStatistics;
import state.member.domain.repository.KstupInstPbancRegStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KstartupGetBizPbancRegCntListProcessor {
    private final KstupInstPbancRegStatisticsRepository kstupRepo;

    public List<KstupInstPbancRegStatistics> execute() {
        return kstupRepo.findAllByOrderByBaseDtAsc();
    }
}
