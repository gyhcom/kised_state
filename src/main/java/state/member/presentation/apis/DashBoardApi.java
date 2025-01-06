package state.member.presentation.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import state.member.application.fasade.DashboardManager;
import state.member.presentation.request.TempRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class DashBoardApi {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
