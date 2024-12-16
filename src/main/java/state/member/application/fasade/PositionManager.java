package state.member.application.fasade;

import org.springframework.stereotype.Service;
import state.member.application.processor.position.PositionFindAllProcessor;
import state.member.application.processor.position.PositionFindByIdProcessor;
import state.member.domain.entity.Position;

import java.util.List;
import java.util.Optional;

@Service
public class PositionManager {
    private final PositionFindAllProcessor positionFindAllProcessor;
    private final PositionFindByIdProcessor positionFindByIdProcessor;

    public PositionManager(
            PositionFindAllProcessor positionFindAllProcessor,
            PositionFindByIdProcessor positionFindByIdProcessor) {
        this.positionFindAllProcessor = positionFindAllProcessor;
        this.positionFindByIdProcessor = positionFindByIdProcessor;
    }

    public List<Position> findAll() {
        return positionFindAllProcessor.execute();
    }

    public Position findById(String id) {return positionFindByIdProcessor.execute(id);}
}
