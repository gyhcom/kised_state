package state.member.domain.repository;

import state.member.domain.entity.BatchHis;

import java.util.List;

public interface BatchHisRepository {
    List<BatchHis> batchHisList(String stepName, String status, String startTime, String endTime);
}
