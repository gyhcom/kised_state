package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.member.application.command.MemberRegisterCommand;

@Setter
@Getter
public class MemberRegisterRequest {
    int seq;
    String userId;
    String username;
    String password;
    String userRole;
    String email;
    String departmentCode;
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
