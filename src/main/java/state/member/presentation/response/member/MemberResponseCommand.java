package state.member.presentation.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseCommand {
    int seq;
    String userId;
    String username;
    String userRole;
    String email;
    String departmentCode;
    String positionCode;

    public MemberResponseCommand(
            int seq,
            String userId,
            String username,
            String userRole,
            String email,
            String departmentCode,
            String positionCode) {
        this.seq = seq;
        this.userId = userId;
        this.username = username;
        this.userRole = userRole;
        this.email = email;
        this.departmentCode = departmentCode;
        this.positionCode = positionCode;
    }
}
