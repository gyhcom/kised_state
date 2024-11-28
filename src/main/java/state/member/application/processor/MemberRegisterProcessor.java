package state.member.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.member.application.command.MemberRegisterCommand;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberRegisterProcessor {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberRegisterProcessor(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(MemberRegisterCommand memberRegisterCommand) {
        memberRegisterCommand.setPassword(passwordEncoder.encode(memberRegisterCommand.getPassword()));
        memberRepository.save(memberRegisterCommand.toEntity(memberRegisterCommand));
    }
}
