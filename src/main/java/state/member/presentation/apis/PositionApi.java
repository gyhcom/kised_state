package state.member.presentation.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import state.member.application.fasade.PositionManager;
import state.member.domain.entity.Department;
import state.member.domain.entity.Position;
import state.member.presentation.response.PositionResponse;

import java.util.List;

@RequestMapping("/psit")
@Controller
public class PositionApi {
    private final PositionManager positionManager;

    public PositionApi(PositionManager positionManager) {
        this.positionManager = positionManager;
    }

    @GetMapping("/psitInfo")
    public ResponseEntity<List<PositionResponse>> psitInfo() {
        return new ResponseEntity<>(new Position().toCommand(positionManager.findAll()), HttpStatus.OK);
    }
}
