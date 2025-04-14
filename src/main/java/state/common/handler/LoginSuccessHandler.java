package state.common.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import state.member.presentation.MemberAuth;

import java.io.IOException;

@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

//        MemberAuth member = (MemberAuth) authentication.getPrincipal();
//
//        if(member.validate()) {
//            session.setAttribute("seq", member.getSeq());
//            session.setAttribute("userId", member.getUsername());
//            session.setAttribute("username", member.getRealUsername());
//            session.setAttribute("auth", member.getAuthorities());
//            session.setAttribute("email", member.getEmail());
//            session.setAttribute("deptCd", member.getDeptCd());
//            session.setAttribute("psitCd", member.getPsitCd());
//        }

        response.sendRedirect("/dashboard");
    }
}
