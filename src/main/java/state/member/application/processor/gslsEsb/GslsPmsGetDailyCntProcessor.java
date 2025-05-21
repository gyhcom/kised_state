package state.member.application.processor.gslsEsb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.GslsPmsCountStatistics;
import state.member.domain.repository.GslsPmsCountStatisticsRepository;

import java.util.List;

/**
 * 국고보조금(PMS) 최근 30일 통계 조회
 * 1. 정보공시내역 건수
 * 2. 세입세출내역 건수
 * 3. 재무제표결산 내역 건수
 * 4. 수급자집행정보 건수
 * 5. 상세내역사업정보 건수
 */
@RequiredArgsConstructor
@Component
public class GslsPmsGetDailyCntProcessor {
    private final GslsPmsCountStatisticsRepository gslsPmsRepo;

    public List<GslsPmsCountStatistics> execute() {
        return gslsPmsRepo.findTop30ByOrderByBaseDtAsc();
    }
}
