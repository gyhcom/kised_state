package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import state.member.application.processor.startbiz.StartbizBizStatsInfoApiProcessor;

import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class StartbizManager {
    private final StartbizBizStatsInfoApiProcessor startbizBizStatsInfoApiProcessor;

    public Mono<Map<String, Object>> bizStatsInfoApi() {
        return startbizBizStatsInfoApiProcessor.execute();
    }
}
