package state.admin.userManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
@Builder
public class UserInfoDeleteCommand {

    private int seq;
    private AuthRole userRole;
}
