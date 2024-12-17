package state.admin.userManage.presentation.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull
    private String userRole;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "부서를 선택해주세요.")
    private String departmentCode;

    @NotBlank(message = "직급을 선택해주세요.")
    private String positionCode;

    public UserRegisterCommand toCommand(UserInfoRegisterRequest userInfoRegisterRequest) {
        return UserRegisterCommand.builder()
                .userId(userInfoRegisterRequest.userId)
                .username(userInfoRegisterRequest.username)
                .password(userInfoRegisterRequest.password)
                .userRole(userInfoRegisterRequest.userRole)
                .email(userInfoRegisterRequest.email)
                .departmentCode(userInfoRegisterRequest.departmentCode)
                .positionCode(userInfoRegisterRequest.positionCode)
                .build();
    }

}
