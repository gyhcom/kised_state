package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.DataImport;
import state.member.domain.repository.DataImportLogRepository;
import state.member.infrastructure.JPA.JpaDataImportLogRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DataImportLogRepositoryAdapter implements DataImportLogRepository {
    private final JpaDataImportLogRepository jpa;

    @Override
    public void save(DataImport log) {
        jpa.save(log);
    }

    @Override
    public List<DataImport> findAll() {
        return jpa.findAll();
    }
}
