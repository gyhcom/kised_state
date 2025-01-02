package state.admin.memberManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.domain.auth.AuthRole;

@Getter
@Setter
@Builder
public class MemberMngDeleteCommand {

    private int seq;
    private AuthRole userRole;
}
