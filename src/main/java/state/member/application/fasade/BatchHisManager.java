package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.processor.batchHis.BatchHisGetListProcessor;
import state.member.domain.entity.BatchHis;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BatchHisManager {
    private final BatchHisGetListProcessor batchHisGetListProcessor;

    public List<BatchHis> getBatchHisList(String stepName, String status, String startTime, String endTime) {
        return batchHisGetListProcessor.execute(stepName, status, startTime, endTime);
    }
}
