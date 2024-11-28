package state.member.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseCommand {
    int seq;
    String userId;
    String userRole;

    public MemberResponseCommand(int seq, String userId, String userRole) {
        this.seq = seq;
        this.userId = userId;
        this.userRole = userRole;
    }
}
