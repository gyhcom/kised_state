package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.member.application.command.MemberUpdateCommand;

@Setter
@Getter
public class MemberUpdateRequest {
    int seq;
    String password;
    String email;
    String userRole;
    String departmentCode;
    String positionCode;

    public MemberUpdateCommand toCommand(MemberUpdateRequest memberUpdateRequest) {
        return MemberUpdateCommand.builder()
                .seq(memberUpdateRequest.getSeq())
                .password(memberUpdateRequest.getPassword())
                .email(memberUpdateRequest.getEmail())
                .userRole(memberUpdateRequest.getUserRole())
                .departmentCode(memberUpdateRequest.getDepartmentCode())
                .positionCode(memberUpdateRequest.getPositionCode())
                .build();
    }
}
