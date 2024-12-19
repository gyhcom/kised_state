package state.member.application.command.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.member.domain.entity.Member;

@Builder
@Getter
@Setter
public class MemberModPasswordCommand {
    String currentPassword;
    String newPassword;
}
