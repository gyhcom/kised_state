package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardApi {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
