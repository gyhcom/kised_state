package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.domain.entity.DataImport;
import state.member.domain.repository.DataImportLogRepository;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataImportLogManager {
    private final DataImportLogRepository repository;

    @Transactional
    public void save(DataImport log) {
        log.setCreatedAt(LocalDateTime.now());
        repository.save(log);
    }

    public List<DataImport> getAll() {
        return repository.findAll();
    }
}
