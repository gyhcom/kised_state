package state.admin.userManage.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.application.command.UserInfoUpdateCommand;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
public class UserInfoUpdateRequest {

    private int seq;
    private String userId;
    private String userNm;
    private String password;
    private String userRole;
    private String email;
    private String deptCd;
    private String manageCd;

    public UserInfoUpdateCommand toCommand(UserInfoUpdateRequest userInfoUpdateRequest) {
        return UserInfoUpdateCommand.builder()
                .seq(userInfoUpdateRequest.seq)
                .userNm(userInfoUpdateRequest.userNm)
                .password(userInfoUpdateRequest.password)
                .email(userInfoUpdateRequest.email)
                .deptCd(userInfoUpdateRequest.deptCd)
                .manageCd(userInfoUpdateRequest.manageCd)
                .build();
    }

}
