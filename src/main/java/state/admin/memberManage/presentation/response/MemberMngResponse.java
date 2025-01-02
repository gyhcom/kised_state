package state.admin.memberManage.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.domain.auth.AuthRole;

@Setter
@Getter
@Builder
public class MemberMngResponse {

    private String userId;
    private String username;
    private AuthRole userRole;
    private String email;
    private String deptCd;
    private String manageCd;

    public MemberMngResponse(String userId, String username, AuthRole userRole, String email, String deptCd, String manageCd) {
        this.userId = userId;
        this.username = username;
        this.userRole = userRole;
        this.email = email;
        this.deptCd = deptCd;
        this.manageCd = manageCd;
    }
}
