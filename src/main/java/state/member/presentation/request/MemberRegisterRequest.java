package state.member.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.member.application.command.MemberRegisterCommand;

@Setter
@Getter
public class MemberRegisterRequest {
    int seq;
    String userId;
    String password;
    String userRole;

    public MemberRegisterCommand toCommand(MemberRegisterRequest memberRegisterRequest) {
        return MemberRegisterCommand.builder()
                .userId(memberRegisterRequest.getUserId())
                .password(memberRegisterRequest.getPassword())
                .userRole(memberRegisterRequest.getUserRole())
                .build();
    }
}
