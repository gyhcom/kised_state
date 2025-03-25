package state.member.presentation.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 창업기업확인
 */
@RequestMapping("/cert")
@Controller
public class CertApi {
    @GetMapping
    public String certView() {
        return "services/cert/cert";
    }
}
