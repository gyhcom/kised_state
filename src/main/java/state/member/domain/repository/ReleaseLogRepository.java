package state.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.ReleaseLog;

import java.util.List;
import java.util.Optional;

public interface ReleaseLogRepository {
    List<ReleaseLog> findAll();
    Optional<ReleaseLog> findById(Long id);
    ReleaseLog save(ReleaseLog log);
    void delete(Long id);
}
