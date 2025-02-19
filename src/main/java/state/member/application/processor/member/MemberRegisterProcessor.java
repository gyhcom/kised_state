package state.member.application.processor.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.command.member.MemberRegisterCommand;
import state.member.domain.exception.custom.DuplicateEmailException;
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
            throw new ApiException(ErrorCode.ID_CHECK);
        }

        // 이메일 중복 체크
        if(memberRepository.existsByEmail(memberRegisterCommand.getEmail())){
            throw new DuplicateEmailException();
        }

        memberRegisterCommand.setPassword(passwordEncoder.encode(memberRegisterCommand.getPassword()));
        memberRepository.save(memberRegisterCommand.toEntity(memberRegisterCommand));
    }
}
