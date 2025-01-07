package state.member.presentation.apis;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Slf4j
@Controller
public class DashBoardApi {
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 관리자 여부 확인
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_SUPER"));

        // 관리자일 경우 'auth' 값을 세션에 설정
        if (isAdmin) {
            session.setAttribute("auth", "Y");
        } else {
            session.setAttribute("auth", "N");
        }

        return "dashboard";
    }
}
