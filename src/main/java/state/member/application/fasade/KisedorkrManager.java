package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import state.member.application.processor.kisedorkr.KisedorkrDurationProcessor;

import java.util.Map;

@Transactional
@Service
public class KisedorkrManager {
    private final KisedorkrDurationProcessor kisedorkrDurationProcessor;

    public KisedorkrManager(KisedorkrDurationProcessor kisedorkrDurationProcessor) {
        this.kisedorkrDurationProcessor = kisedorkrDurationProcessor;
    }

    public Flux<Map<String, Object>> getAnnulData() {
        return kisedorkrDurationProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getMonthlyData(String year) {
        return kisedorkrDurationProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getWeeklyData(String year, String month) {
        return kisedorkrDurationProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getGridData() {
        return kisedorkrDurationProcessor.getGridData();
    }
}
