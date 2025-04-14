package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.member.application.processor.cert.CertCntProcessor;

import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class CertManager {
    private final CertCntProcessor certCntProcessor;

    public Mono<Map<String, Object>> getCertCnt() {
        return certCntProcessor.execute();
    }
}
