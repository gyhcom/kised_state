package state.member.presentation.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import state.member.application.command.member.MemberRegisterCommand;

@Setter
@Getter
public class MemberRegisterRequest {
    int seq;

    @NotBlank(message = "아이디를 입력해주세요.")
    String userId;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    //TODO 최초 등록 시 "USER"로 등록되도록 수정
    // 관리자 모듈에 권한이 enum으로 관리되면 좋을 것 같고 추후 해당 enum에서 값을 가져오도록 수정하기
    final String userRole = "ROLE_USER";

    @Email(message = "이메일 형식을 확인해주세요.")
    String email;

    @NotBlank(message = "소속된 부서를 선택해주세요.")
    String departmentCode;

    @NotBlank(message = "직위를 선택해주세요.")
    String positionCode;

    public MemberRegisterCommand toCommand(MemberRegisterRequest memberRegisterRequest) {
        return MemberRegisterCommand.builder()
                .userId(memberRegisterRequest.getUserId())
                .username(memberRegisterRequest.getUsername())
                .password(memberRegisterRequest.getPassword())
                .userRole(memberRegisterRequest.getUserRole())
                .email(memberRegisterRequest.getEmail())
                .departmentCode(memberRegisterRequest.getDepartmentCode())
                .positionCode(memberRegisterRequest.getPositionCode())
                .build();
    }
}
