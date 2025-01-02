package state.admin.memberManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.domain.auth.AuthRole;

@Setter
@Getter
@Builder
public class MemberMngUpdateCommand {

    private int seq;
    private String userId;
    private String username;
    private String password;
    private AuthRole userRole;
    private String email;
    private String departmentCode;
    private String positionCode;
}
