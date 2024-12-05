package state.member.infrastructure;

import org.springframework.stereotype.Repository;
import state.member.domain.entity.Position;
import state.member.domain.repository.PositionRepository;
import state.member.infrastructure.JPA.JpaPositionRepository;

import java.util.List;

@Repository
public class PositionRepositoryAdapter implements PositionRepository {
    private final JpaPositionRepository jpaPositionRepository;

    public PositionRepositoryAdapter(JpaPositionRepository jpaPositionRepository) {
        this.jpaPositionRepository = jpaPositionRepository;
    }

    @Override
    public List<Position> findAll() {
        return jpaPositionRepository.findAll();
    }

    @Override
    public Boolean existsById(String id) {
        return jpaPositionRepository.existsById(id);
    }
}
