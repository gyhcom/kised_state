package state.member.application.command.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberUpdateCommand {
    int seq;
    String password;
    String email;
    String departmentCode;
    String positionCode;
    String userRole;
}
