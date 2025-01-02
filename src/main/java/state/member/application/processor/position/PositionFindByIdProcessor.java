package state.member.application.processor.position;

import org.springframework.stereotype.Component;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.domain.entity.Position;
import state.member.domain.exception.custom.PositionNotExistException;
import state.member.domain.repository.PositionRepository;

import java.util.Optional;

@Component
public class PositionFindByIdProcessor {
    private final PositionRepository positionRepository;
    public PositionFindByIdProcessor(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position execute(String id) {
        if(!positionRepository.existsById(id)) throw new PositionNotExistException();

        return positionRepository.getReferenceById(id);
    }
}
