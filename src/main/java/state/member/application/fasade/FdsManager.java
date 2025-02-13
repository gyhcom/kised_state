package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.fds.FdsDetCntProcessor;
import state.member.application.processor.fds.FdsDetInfoProcessor;
import state.member.application.processor.fds.FdsDetProfCntProcessor;
import state.member.application.processor.fds.FdsTotDetCntProcessor;

import java.util.Map;

@Transactional
@Service
public class FdsManager {
    private final FdsDetInfoProcessor fdsDetInfoProcessor;
    private final FdsDetCntProcessor fdsDetCntProcessor;
    private final FdsDetProfCntProcessor fdsDetProfCntProcessor;
    private final FdsTotDetCntProcessor fdsTotDetCntProcessor;

    public FdsManager(FdsDetInfoProcessor fdsDetInfoProcessor,
                      FdsDetCntProcessor fdsDetCntProcessor,
                      FdsDetProfCntProcessor fdsDetProfCntProcessor,
                      FdsTotDetCntProcessor fdsTotDetCntProcessor) {
        this.fdsDetInfoProcessor = fdsDetInfoProcessor;
        this.fdsDetCntProcessor = fdsDetCntProcessor;
        this.fdsDetProfCntProcessor = fdsDetProfCntProcessor;
        this.fdsTotDetCntProcessor = fdsTotDetCntProcessor;
    }

    public Flux<Map<String, Object>> getDetInfo(String year, String month) {
        return fdsDetInfoProcessor.execute(year, month);
    }

    public Flux<Map<String, Object>> getDetAnnCnt() {
        return fdsDetCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDetMonCnt(String year) {
        return fdsDetCntProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getDetProfAnnCnt() {
        return fdsDetProfCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getDetProfMonCnt(String year) {
        return fdsDetProfCntProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getTotDetAnnCnt() {
        return fdsTotDetCntProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getTotDetMonCnt(String year) {
        return fdsTotDetCntProcessor.monthlyExecute(year);
    }
}
