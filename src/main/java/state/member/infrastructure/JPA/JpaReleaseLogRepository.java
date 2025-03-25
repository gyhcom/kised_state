package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.ReleaseLog;

public interface JpaReleaseLogRepository extends JpaRepository<ReleaseLog, Long> {
}
