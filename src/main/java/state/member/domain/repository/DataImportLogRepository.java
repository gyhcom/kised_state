package state.member.domain.repository;


import state.member.domain.entity.DataImport;

import java.util.List;

public interface DataImportLogRepository {
    void save(DataImport log);

    List<DataImport> findAll();
}
