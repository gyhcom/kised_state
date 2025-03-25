package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/incorp")
@Controller
public class IncorpApi {
    @GetMapping
    public String incorpView() {
        return "services/incorp/incorp";
    }
}
