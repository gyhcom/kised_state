package state.common.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import state.member.application.fasade.MemberManager;
import state.member.domain.entity.Member;
import state.member.presentation.MemberAuth;

import java.util.Collections;

@Service
public class PrincipalDetailService implements UserDetailsService {
    private final MemberManager memberManager;

    public PrincipalDetailService(MemberManager memberManager) {
        this.memberManager = memberManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberManager.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다. - username : " + username);
        });

        return MemberAuth.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(member.getUserRole())))
                .build();
    }
}
