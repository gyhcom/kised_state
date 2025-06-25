package state.member.application.processor.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.TabCommStats;
import state.member.domain.repository.TabCommStatsRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TabCommStatsSaveProcessor {
    private final TabCommStatsRepository repo;

    public void execute(List<TabCommStats> entityList) {
        repo.saveAll(entityList);
    }
}
