package state.member.presentation.apis;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import state.common.exception.ErrorCode;

@Slf4j
@Controller
public class LoginApi {
    @GetMapping("/loginForm")
    public String loginForm() {
        return "login";
    }

    /**
     * 로그인에 실패했을 경우 LoginFailureHandler에서 호출하는 loginForm
     * @param mv
     * @param request
     * @return
     */
    @PostMapping("/loginForm")
    public ModelAndView loginForm(ModelAndView mv, HttpServletRequest request) {
        String errorMessage = (String) request.getAttribute("loginFailMsg");

        // RedirectAttributes으로 수정 생각해보기

        mv.addObject("loginFailMsg", errorMessage);
        mv.setViewName("login");
        return mv;
    }
}