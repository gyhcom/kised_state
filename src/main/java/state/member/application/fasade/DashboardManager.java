package state.member.application.fasade;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.processor.dashboard.system1.DashboardGetSystem1DataProcessor;
import state.member.application.processor.dashboard.system2.DashboardGetSystem2DataProcessor;
import state.member.presentation.request.TempRequestDto;

import java.util.List;

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
    public Flux<TempRequestDto> getSystems1UsageData() {
        return dashboardGetSystem1DataProcessor.execute();
    }

    public Flux<TempRequestDto> getSystems2UsageData() {
        return dashboardGetSystem2DataProcessor.execute();
    }
}
