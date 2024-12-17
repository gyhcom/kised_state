package state.common.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import state.common.handler.LoginFailureHandler;
import state.common.handler.LoginSuccessHandler;
import state.member.application.fasade.MemberManager;
import state.member.presentation.MemberAuth;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class StateSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/loginForm").permitAll()
                        .requestMatchers("/dashboard").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 권한 필요
                        .requestMatchers("/member/**").hasRole("USER") // 사용자 권한 필요
                        .requestMatchers("/edu/**").permitAll()       // 공용 접근 허용
                        .anyRequest().authenticated()                // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/loginPost")
                        .defaultSuccessUrl("/dashboard", true) // 로그인 성공 후 이동할 URL
                        //.failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
                        .successHandler((getSuccessHandler()))
                        .failureHandler(getFailureHandler())
                        .usernameParameter("userId") // userId 사용
                        .passwordParameter("password") // password 유지
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loginForm")
                        .invalidateHttpSession(true)// 세션 무효화
                        .deleteCookies("JSESSIONID")
                )
                //.httpBasic(withDefaults())
        ;
        //TODO: 인증 부분 토큰방식으로 구현 필요
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberManager memberManager){
        return userId  -> memberManager
                .findByUserId(userId)
                .map(member -> MemberAuth.builder()
                        .seq(member.getSeq())
                        .username(member.getUserId())
                        .realUsername(member.getUsername())
                        .password(member.getPassword())
                        .email(member.getEmail())
                        .deptCd(member.getDepartmentCode())
                        .psitCd(member.getPositionCode())
                        // 이 부분 알고 쓰기
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getUserRole())))
                        .build()
                )
                // Exception 직접 구현하기
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - userId  : " + userId ));
    }

    @Bean
    LoginSuccessHandler getSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    LoginFailureHandler getFailureHandler() {
        return new LoginFailureHandler();
    }
}
