package state.member.application.fasade;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.dashboard.system1.DashboardGetSystem1DataProcessor;
import state.member.application.processor.dashboard.system2.DashboardGetSystem2DataProcessor;
import state.member.presentation.request.TempRequestDto;

import java.util.List;
import java.util.Map;

@Service
public class DashboardManager {
    private final DashboardGetSystem1DataProcessor dashboardGetSystem1DataProcessor;
    private final DashboardGetSystem2DataProcessor dashboardGetSystem2DataProcessor;

    public DashboardManager(
            DashboardGetSystem1DataProcessor dashboardGetSystem1DataProcessor,
            DashboardGetSystem2DataProcessor dashboardGetSystem2DataProcessor
    ) {
        this.dashboardGetSystem1DataProcessor = dashboardGetSystem1DataProcessor;
        this.dashboardGetSystem2DataProcessor = dashboardGetSystem2DataProcessor;
    }
    public Flux<TempRequestDto> getSystems1AnnulData() {
        return dashboardGetSystem1DataProcessor.annualExecute();
    }

    public Flux<TempRequestDto> getSystem1MonthlyData(String year) {
        return dashboardGetSystem1DataProcessor.monthlyExecute(year);
    }

    public Flux<TempRequestDto> getSystem1WeeklyData(String year, String month) {
        return dashboardGetSystem1DataProcessor.weeklyExecute(year, month);
    }

    public Flux<Map<String, Object>> getGridData() {
        return dashboardGetSystem1DataProcessor.getGridData();
    }

    public Flux<TempRequestDto> getSystems2AnnulData() {
        return dashboardGetSystem2DataProcessor.annualExecute();
    }

    public Flux<TempRequestDto> getSystem2MonthlyData(String year) {
        return dashboardGetSystem2DataProcessor.monthlyExecute(year);
    }

    public Flux<TempRequestDto> getSystem2WeeklyData(String year, String month) {
        return dashboardGetSystem2DataProcessor.weeklyExecute(year, month);
    }
}
