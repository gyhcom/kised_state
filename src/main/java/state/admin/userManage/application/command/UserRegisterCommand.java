package state.admin.userManage.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;
import state.member.domain.entity.Member;

@Setter
@Getter
@Builder
public class UserRegisterCommand {

    private String userId;
    private String username;
    private String password;
    private String userRole;
    private String email;
    private String departmentCode;
    private String positionCode;

    public Member toEntity(UserRegisterCommand userRegisterCommand) {
        return Member.builder()
                .userId(userRegisterCommand.userId)
                .username(userRegisterCommand.username)
                .password(userRegisterCommand.password)
                .userRole(userRegisterCommand.userRole)
                .departmentCode(userRegisterCommand.departmentCode)
                .positionCode(userRegisterCommand.positionCode)
                .email(userRegisterCommand.email)
                .deleteYn("N")
                .build();
    }
}
