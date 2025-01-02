package state.admin.memberManage.presentation.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.application.command.MemberMngUpdateCommand;
import state.admin.memberManage.domain.auth.AuthRole;

@Getter
@Setter
public class MemberMngUpdateRequest {

    private int seq;
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthRole userRole;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "부서를 선택해주세요.")
    private String departmentCode;

    @NotBlank(message = "직급을 선택해주세요.")
    private String positionCode;

    public MemberMngUpdateCommand toCommand(MemberMngUpdateRequest memberMngUpdateRequest) {
        return MemberMngUpdateCommand.builder()
                .seq(memberMngUpdateRequest.seq)
                .username(memberMngUpdateRequest.username)
                .password(memberMngUpdateRequest.password)
                .email(memberMngUpdateRequest.email)
                .userRole(memberMngUpdateRequest.userRole)
                .departmentCode(memberMngUpdateRequest.departmentCode)
                .positionCode(memberMngUpdateRequest.positionCode)
                .build();
    }

}
