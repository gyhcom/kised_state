package state.member.application.command.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDeleteCommand {
    int seq;
    String userId;
    String username;
}
