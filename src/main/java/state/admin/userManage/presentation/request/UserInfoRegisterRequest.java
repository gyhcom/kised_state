package state.admin.userManage.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
public class UserInfoRegisterRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userNm;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String userRole;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "부서를 선택해주세요.")
    private String deptCd;

    @NotBlank(message = "직급을 선택해주세요.")
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
