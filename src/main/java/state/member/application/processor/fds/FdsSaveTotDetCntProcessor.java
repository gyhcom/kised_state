package state.member.application.processor.fds;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.FdsCntStats;
import state.member.domain.repository.FdsCntStatsRepository;

@RequiredArgsConstructor
@Component
public class FdsSaveTotDetCntProcessor {
    private final FdsCntStatsRepository fdsRepo;

    /**
     * 사업비점검 이상거래 탐지 데이터 적재
     * 총 이상거래 탐지 건수
     * 일일 배치
     */
    public void execute(FdsCntStats entity) {
        fdsRepo.saveDetTotCnt(entity);
    }
}
