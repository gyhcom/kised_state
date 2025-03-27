package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.DataImport;

public interface JpaDataImportLogRepository extends JpaRepository<DataImport, Long> {

}
