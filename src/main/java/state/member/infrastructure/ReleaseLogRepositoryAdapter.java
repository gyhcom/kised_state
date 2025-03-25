package state.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.ReleaseLog;
import state.member.domain.repository.ReleaseLogRepository;
import state.member.infrastructure.JPA.JpaReleaseLogRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReleaseLogRepositoryAdapter implements ReleaseLogRepository {
    private final JpaReleaseLogRepository jpa;

    @Override
    public List<ReleaseLog> findAll() {
        return jpa.findAll();
    }

    @Override
    public Optional<ReleaseLog> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public ReleaseLog save(ReleaseLog log) {
        return jpa.save(log);
    }


    @Override
    public void delete(Long id) {
        jpa.deleteById(id);
    }
}
