package state.member.domain.repository;

import state.member.domain.entity.Position;

import java.util.List;

public interface PositionRepository {
    List<Position> findAll();

    Boolean existsById(String id);
}
