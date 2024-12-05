package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Deprecated //미리 구현한 컨트롤러. 화면이 구현되면 사용할 예정
@Controller
public class LoginApi {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}