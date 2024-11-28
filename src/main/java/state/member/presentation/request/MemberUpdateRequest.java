package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.member.application.command.MemberUpdateCommand;

@Setter
@Getter
public class MemberUpdateRequest {
    int seq;
    String password;
    String userRole;

    public MemberUpdateCommand toCommand(MemberUpdateRequest memberUpdateRequest) {
        return MemberUpdateCommand.builder()
                .seq(memberUpdateRequest.getSeq())
                .password(memberUpdateRequest.getPassword())
                .userRole(memberUpdateRequest.getUserRole())
                .build();
    }
}
