package state.member.application.processor.fds;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.FdsDetCntByTypeStats;
import state.member.domain.repository.FdsDetCntByTypeStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FdsSaveDetCntByTypeProcessor {
    private final FdsDetCntByTypeStatsRepository fdsRepo;

    /**
     * 사업비점검 이상거래 탐지 데이터 적재
     * 유형별 탐지 건수 적재
     * 일일 배치
     * @param entityList
     */
    public void execute(List<FdsDetCntByTypeStats> entityList) {
        fdsRepo.saveDetCntByType(entityList);
    }
}
