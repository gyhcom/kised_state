package state.member.presentation;

import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;

import java.util.Collection;

@ToString
@Builder
@Setter
public class MemberAuth implements UserDetails {
    private int seq = -1;
    private String password;
    private String username; // loadUserByUsername에서 사용하는 username 실제론 userId가 세팅됨
    private String realUsername; // userId가 아닌 실제 username이 담길 변수
    private String email;
    private String deptCd;
    private String psitCd;
    Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getSeq() {return seq;}

    public String getRealUsername() {
        return realUsername;
    }

    public String getEmail() {
        return email;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public String getPsitCd() {
        return psitCd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean validate() {
        // 어떤 값이 존재하지 않는지 명확히 표현하기 위해 if - else if 사용
        if(password.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "Password가 존재하지 않습니다.");
        } else if(seq == -1) {
            throw new ApiException(ErrorCode.SERVER_ERROR);
        } else if(username.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "Username이 존재하지 않습니다.");
        } else if(realUsername.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "realUsername이 존재하지 않습니다.");
        } else if(email.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "Email이 존재하지 않습니다.");
        } else if(deptCd.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "부서코드가 존재하지 않습니다.");
        } else if(psitCd.isBlank()) {
            throw new ApiException(ErrorCode.NULL_POINT, "직위코드가 존재하지 않습니다.");
        }
        return true;
    }
}
