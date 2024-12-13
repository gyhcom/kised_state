package state.admin.userManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;

@Setter
@Getter
@Builder
public class UserInfoUpdateCommand {

    private int seq;
    private String userId;
    private String username;
    private String password;
    private AuthRole userRole;
    private String email;
    private String departmentCode;
    private String positionCode;
}
