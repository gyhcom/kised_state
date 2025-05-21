package state.member.application.processor.kstartup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.KstupInstPbancRegStatistics;
import state.member.domain.repository.KstupInstPbancRegStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KstartupSaveBizPbancRegCntListProcessor {
    private final KstupInstPbancRegStatisticsRepository kstupRepo;

    public void execute(List<KstupInstPbancRegStatistics> entityList) {
        kstupRepo.saveAll(entityList);
    }
}
