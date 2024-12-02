package state.admin.userManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;
import state.admin.userManage.domain.entity.Member;

@Setter
@Getter
@Builder
public class UserRegisterCommand {

    private String userId;
    private String userNm;
    private String password;
    private AuthRole userRole;
    private String email;
    private String deptCd;
    private String manageCd;

    public Member toEntity(UserRegisterCommand userRegisterCommand) {
        return Member.builder()
                .userId(userRegisterCommand.userId)
                .userNm(userRegisterCommand.userNm)
                .password(userRegisterCommand.password)
                .userRole(userRegisterCommand.userRole)
                .email(userRegisterCommand.email)
                .deptCd(userRegisterCommand.deptCd)
                .manageCd(userRegisterCommand.manageCd)
                .build();
    }
}
