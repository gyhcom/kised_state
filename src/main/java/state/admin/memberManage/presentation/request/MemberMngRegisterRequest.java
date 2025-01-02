package state.admin.memberManage.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.application.command.MemberMngRegisterCommand;

@Getter
@Setter
public class MemberMngRegisterRequest {

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

    public MemberMngRegisterCommand toCommand(MemberMngRegisterRequest userInfoRegisterRequest) {
        return MemberMngRegisterCommand.builder()
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
