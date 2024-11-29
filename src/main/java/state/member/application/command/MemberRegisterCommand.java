package state.member.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.member.domain.entity.Member;

@Setter
@Getter
@Builder
public class MemberRegisterCommand {
    int seq;
    String userId;
    String username;
    String password;
    String userRole;
    String email;
    String departmentCode;
    String positionCode;

    public Member toEntity(MemberRegisterCommand memberRegisterCommand) {
        return Member.builder()
                .userId((memberRegisterCommand.getUserId()))
                .username(memberRegisterCommand.getUsername())
                .password(memberRegisterCommand.getPassword())
                .userRole(memberRegisterCommand.getUserRole())
                .email(memberRegisterCommand.getEmail())
                .departmentCode(memberRegisterCommand.getDepartmentCode())
                .positionCode(memberRegisterCommand.getPositionCode())
                .build();
    }
}
