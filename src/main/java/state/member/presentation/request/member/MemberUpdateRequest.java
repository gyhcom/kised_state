package state.member.presentation.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import state.member.application.command.member.MemberUpdateCommand;

@Setter
@Getter
public class MemberUpdateRequest {
    int seq;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    String username;

//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    String password;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일 정보를 입력해주세요.")
    String email;

    public MemberUpdateCommand toCommand(MemberUpdateRequest memberUpdateRequest) {
        return MemberUpdateCommand.builder()
                .seq(memberUpdateRequest.getSeq())
                .username(memberUpdateRequest.getUsername())
                //.password(memberUpdateRequest.getPassword())
                .email(memberUpdateRequest.getEmail())
                .build();
    }
}
