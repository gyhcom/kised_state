package state.member.presentation.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginApi {
    @GetMapping("/loginForm")
    public String loginForm() {
        return "login";
    }
}