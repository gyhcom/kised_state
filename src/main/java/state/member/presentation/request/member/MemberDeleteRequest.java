package state.member.presentation.request.member;

import lombok.Getter;
import lombok.Setter;
import state.member.application.command.member.MemberDeleteCommand;

@Setter
@Getter
public class MemberDeleteRequest {
    private int seq;
    private String userId;
    private String username;

    public MemberDeleteCommand toCommand(MemberDeleteRequest memberDeleteRequest) {
        return MemberDeleteCommand.builder()
                .seq(memberDeleteRequest.getSeq())
                .userId(memberDeleteRequest.getUserId())
                .username(memberDeleteRequest.getUsername())
                .build();
    }
}
