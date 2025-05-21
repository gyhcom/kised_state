package state.member.application.processor.cert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.CertCountStatistics;
import state.member.domain.repository.CertCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CertGetCntListProcessor {
    private final CertCountStatisticsRepository certRepository;

    /**
     * 창업기업확인 일일 통계 데이터 조회
     * @return
     */
    public List<CertCountStatistics> execute() {
        return certRepository.findTop30ByOrderByBaseDtAsc();
    }
}
