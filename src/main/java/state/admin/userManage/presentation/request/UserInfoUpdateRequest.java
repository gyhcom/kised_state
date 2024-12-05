package state.admin.userManage.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.application.command.UserInfoUpdateCommand;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
public class UserInfoUpdateRequest {

    private int seq;
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userNm;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 12, message = "대/소문자, 특수문자 포함 최대 12차리 입력해주세요.")
    private String password;

    @NotBlank
    private String userRole;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "부서를 선택해주세요.")
    private String deptCd;

    @NotBlank(message = "직급을 선택해주세요.")
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
