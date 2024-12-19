package state.member.application.processor.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.member.application.command.member.MemberModPasswordCommand;
import state.member.domain.entity.Member;
import state.member.domain.exception.custom.MemberNotFoundException;
import state.member.domain.exception.custom.PasswordNotMatchedExcpetion;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberModifyPasswordProcessor {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberModifyPasswordProcessor(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String userId, String email, MemberModPasswordCommand memberModPasswordCommand) {
        Member member = memberRepository.findByUserIdAndEmail(userId, email)
                .orElseThrow(() -> new MemberNotFoundException());

        // 현재 비밀번호와 입력된 비밀번호가 같은지 체크
        if(!passwordEncoder.matches(memberModPasswordCommand.getCurrentPassword(), member.getPassword())) {
            throw new PasswordNotMatchedExcpetion();
        }

        memberModPasswordCommand.setNewPassword(passwordEncoder.encode(memberModPasswordCommand.getNewPassword()));

        member.setPassword(memberModPasswordCommand.getNewPassword());
    }
}
