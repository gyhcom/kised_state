package state.member.application.processor.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.member.application.command.member.MemberRegisterCommand;
import state.member.domain.exception.DuplicateEmailException;
import state.member.domain.exception.DuplicateUserIdException;
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
        // ID 중복 체크
        if(memberRepository.existsByUserId(memberRegisterCommand.getUserId())) {
            throw new DuplicateUserIdException();
        }

        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberRegisterCommand.getEmail())){
            throw new DuplicateEmailException();
        }

        memberRegisterCommand.setPassword(passwordEncoder.encode(memberRegisterCommand.getPassword()));
        memberRepository.save(memberRegisterCommand.toEntity(memberRegisterCommand));
    }
}
