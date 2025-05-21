package state.member.application.processor.batchHis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.BatchHis;
import state.member.domain.repository.BatchHisRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BatchHisGetListProcessor {
    private final BatchHisRepository batchRepo;

    public List<BatchHis> execute(String stepName, String status, String startTime, String endTime) {
        return batchRepo.batchHisList(stepName, status, startTime, endTime);
    }
}
