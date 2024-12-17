package state.member.domain.repository;

import state.member.domain.entity.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository {
    List<Position> findAll();

    Boolean existsById(String id);

    Position getReferenceById(String id);
}
