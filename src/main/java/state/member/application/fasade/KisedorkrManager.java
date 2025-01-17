package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import state.member.application.processor.kisedorkr.KisedorkrApiProcessor;

import java.util.Map;

@Transactional
@Service
public class KisedorkrManager {
    private final KisedorkrApiProcessor kisedorkrApiProcessor;

    public KisedorkrManager(KisedorkrApiProcessor kisedorkrApiProcessor) {
        this.kisedorkrApiProcessor = kisedorkrApiProcessor;
    }

    public Flux<Map<String, Object>> getAnnulData() {
        return kisedorkrApiProcessor.annualExecute();
    }

    public Flux<Map<String, Object>> getMonthlyData(String year) {
        return kisedorkrApiProcessor.monthlyExecute(year);
    }

    public Flux<Map<String, Object>> getWeeklyData(String year, String month) {
        return kisedorkrApiProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getGridData() {
        return kisedorkrApiProcessor.getGridData();
    }
}
