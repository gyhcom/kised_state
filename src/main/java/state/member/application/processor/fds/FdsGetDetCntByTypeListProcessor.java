package state.member.application.processor.fds;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.FdsDetCntByTypeStats;
import state.member.domain.repository.FdsDetCntByTypeStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FdsGetDetCntByTypeListProcessor {
    private final FdsDetCntByTypeStatsRepository fdsRepo;

    /**
     * 현황 통계 DB 조회
     * 이상거래탐지 유형별 탐지 건수 조회
     * @return
     */
    public List<FdsDetCntByTypeStats> execute() {
        return fdsRepo.findAllByOrderByBaseDtDesc();
    }
}
