package state.member.application.processor.position;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Position;
import state.member.domain.exception.custom.DepartmentNotExistException;
import state.member.domain.exception.custom.PositionNotExistException;
import state.member.domain.repository.PositionRepository;

import java.util.List;

@Component
public class PositionFindAllProcessor {
    private final PositionRepository positionRepository;

    public PositionFindAllProcessor(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> execute() {
        List<Position> positions = positionRepository.findAll();
        if(positions.isEmpty()) {
            throw new PositionNotExistException();
        }
        return positions;
    }
}
