package state.member.application.processor.cert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.CertCountStatistics;
import state.member.domain.repository.CertCountStatisticsRepository;

@RequiredArgsConstructor
@Component
public class CertSaveCntProcessor {
    private final CertCountStatisticsRepository certRepository;

    public void execute(CertCountStatistics cert) {
        certRepository.save(cert);
    }
}
