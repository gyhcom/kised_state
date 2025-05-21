package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.CertCountStatistics;
import state.member.domain.repository.CertCountStatisticsRepository;
import state.member.infrastructure.JPA.JpaCertCountStatisticsRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CertCountStatisticsRepositoryAdapter implements CertCountStatisticsRepository {
    private final JpaCertCountStatisticsRepository jpaCertCountStatisticsRepository;

    @Override
    public void save(CertCountStatistics cert) {
        jpaCertCountStatisticsRepository.save(cert);
    }

    @Override
    public List<CertCountStatistics> findTop30ByOrderByBaseDtAsc() {
        return jpaCertCountStatisticsRepository.findTop30ByOrderByBaseDtAsc();
    }
}
