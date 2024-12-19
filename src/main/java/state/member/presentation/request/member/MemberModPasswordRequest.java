package state.member.presentation.request.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import state.member.application.command.member.MemberModPasswordCommand;

@Getter
@Setter
public class MemberModPasswordRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    String currentPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    String newPassword;

    public MemberModPasswordCommand toCommand(MemberModPasswordRequest memberModPasswordRequest) {
        return MemberModPasswordCommand.builder()
                .newPassword(memberModPasswordRequest.getNewPassword())
                .currentPassword(memberModPasswordRequest.getCurrentPassword())
                .build();
    }
}
