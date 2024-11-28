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
    String password;
    String userRole;

    public Member toEntity(MemberRegisterCommand memberRegisterCommand) {
        return Member.builder()
                .username(memberRegisterCommand.getUserId())
                .password(memberRegisterCommand.getPassword())
                .userRole(memberRegisterCommand.getUserRole())
                .build();
    }
}
