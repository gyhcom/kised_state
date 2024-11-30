package state.admin.userManage.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
public class UserInfoRegisterRequest {

    private String userId;
    private String userNm;
    private String password;
    private String userRole;
    private String email;
    private String deptCd;
    private String manageCd;

    public UserRegisterCommand toCommand(UserInfoRegisterRequest userInfoRegisterRequest) {
        return UserRegisterCommand.builder()
                .userId(userInfoRegisterRequest.userId)
                .userNm(userInfoRegisterRequest.userNm)
                .password(userInfoRegisterRequest.password)
                .userRole(AuthRole.ADMIN)
                .email(userInfoRegisterRequest.email)
                .deptCd(userInfoRegisterRequest.deptCd)
                .manageCd(userInfoRegisterRequest.manageCd)
                .build();
    }

}
