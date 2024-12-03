package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.Position;

@Repository
public interface JpaPositionRepository extends JpaRepository<Position, String> {
}
