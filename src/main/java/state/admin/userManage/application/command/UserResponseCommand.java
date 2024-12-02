package state.admin.userManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;

@Setter
@Getter
@Builder
public class UserResponseCommand {

    private String userId;
    private String userNm;
    private AuthRole userRole;
    private String email;
    private String deptCd;
    private String manageCd;

    public UserResponseCommand(String userId, String userNm, AuthRole userRole, String email, String deptCd, String manageCd) {
        this.userId = userId;
        this.userNm = userNm;
        this.userRole = userRole;
        this.email = email;
        this.deptCd = deptCd;
        this.manageCd = manageCd;
    }
}
