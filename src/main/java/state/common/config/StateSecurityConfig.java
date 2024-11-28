package state.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 권한 필요
                        .requestMatchers("/member/**").hasRole("USER") // 사용자 권한 필요
                        .requestMatchers("/edu/**").permitAll()       // 공용 접근 허용
                        .anyRequest().authenticated()                // 나머지 요청은 인증 필요
                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.logoutSuccessUrl("/"))
                .httpBasic(withDefaults());
        //TODO: 인증 부분 토큰방식으로 구현 필요
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberManager memberManager){
        return username -> memberManager
                .findByUsername(username)
                .map(member -> MemberAuth.builder()
                        .username(member.getUsername())
                        .password(member.getPassword())
                        // 이 부분 알고 쓰기
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getUserRole())))
                        .build()
                )
                // Exception 직접 구현하기
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - username : " + username));
    }
}
