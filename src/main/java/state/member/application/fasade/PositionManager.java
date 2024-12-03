package state.member.application.fasade;

import org.springframework.stereotype.Service;
import state.member.application.processor.position.PositionFindAllProcessor;
import state.member.domain.entity.Position;

import java.util.List;

@Service
public class PositionManager {
    private final PositionFindAllProcessor positionFindAllProcessor;

    public PositionManager(PositionFindAllProcessor positionFindAllProcessor) {
        this.positionFindAllProcessor = positionFindAllProcessor;
    }

    public List<Position> findAll() {
        return positionFindAllProcessor.execute();
    }
}
