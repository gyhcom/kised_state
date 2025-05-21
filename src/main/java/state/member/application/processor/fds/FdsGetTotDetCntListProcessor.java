package state.member.application.processor.fds;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.repository.FdsCntStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FdsGetTotDetCntListProcessor {
    private final FdsCntStatsRepository fdsRepo;

    /**
     * 사업비점검 이상거래탐지 현황 통계 DB 조회
     * 최근 30일 통계 조회
     * @return
     */
    public List<FdsCntStats> execute() {
        return fdsRepo.findTop30ByOrderByBaseDtAsc();
    }
}
