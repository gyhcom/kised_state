package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.domain.entity.ReleaseLog;
import state.member.domain.repository.ReleaseLogRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseLogManager {
    private final ReleaseLogRepository releaseLogRepository;

    public List<ReleaseLog> getAll() {
        return releaseLogRepository.findAll();
    }

    public ReleaseLog getById(Long id) {
        return releaseLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그 없음: " + id));
    }

    @Transactional
    public void create(ReleaseLog log) {
        log.setCreateDate(LocalDate.from(LocalDateTime.now()));
        releaseLogRepository.save(log);
    }

    @Transactional
    public void update(Long id, ReleaseLog updated) {
        ReleaseLog original = getById(id);
        original.setTitle(updated.getTitle());
        original.setContent(updated.getContent());
    }

    @Transactional
    public void delete(Long id) {
        releaseLogRepository.delete(id);
    }
}
