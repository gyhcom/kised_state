package state.common.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/dashboard").hasAnyRole("USER", "ADMIN")
                        // TODO 외부 API 호출하는 부분은 prefix로 /api를 붙여서 security config에 /api만 허용하면 모든 API 호출 허용되도록 수정하기
                        .requestMatchers("/kisedorkr").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/kstup").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/fds").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN") // 관리자 권한 필요
                        .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN") // 사용자 권한 필요
                        .requestMatchers("/edu/**").permitAll()       // 공용 접근 허용
                        .anyRequest().authenticated()                // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/loginPost")
                        //.defaultSuccessUrl("/dashboard", true) // 로그인 성공 후 이동할 URL
                        //.failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
                        .successHandler((getSuccessHandler()))
                        .failureHandler(getFailureHandler())
                        .usernameParameter("userId") // userId 사용
                        .passwordParameter("password") // password 유지
                        .permitAll()
                        /**
                         * successHandler가 존재하면 defaultSuccessUrl은 무시된다.
                         * successHandler가 우선적으로 실행되기 때문에 로그인 성공 시
                         * defaultSuccessUrl로 이동하는 동작은 수행되지 않는다.
                         */

                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loginForm")
                        .invalidateHttpSession(true)// 세션 무효화
                        .deleteCookies("JSESSIONID")
                )
                // iframe 보안 설정. -> 같은 도메인에서 가져온 html은 iframe을 허용한다
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );
                //.httpBasic(withDefaults());
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

    // DB가 없을 때 임시로 사용될 LoadUserByUserName
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = passwordEncoder();
//
//        // 사용자 생성 (비밀번호를 암호화하여 저장)
//        return new InMemoryUserDetailsManager(
//                org.springframework.security.core.userdetails.User
//                        .withUsername("test")
//                        .password(encoder.encode("1234")) // 암호화된 비밀번호
//                        .roles("ADMIN")
//                        .build()
//        );
//    }

    @Bean
    LoginSuccessHandler getSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    LoginFailureHandler getFailureHandler() {
        return new LoginFailureHandler();
    }
}
