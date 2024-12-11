package state.member.application.processor.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.userManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.application.command.member.MemberUpdateCommand;
import state.member.domain.entity.Member;
import state.member.domain.exception.custom.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberUpdateProcessor {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdateProcessor(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(MemberUpdateCommand memberUpdateCommand) {
        // 사용자 존재 여부 확인
        if(!memberRepository.existsById(memberUpdateCommand.getSeq())) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }

        Member member = memberRepository.getReferenceById(memberUpdateCommand.getSeq());

        //비밀번호 암호화
        memberUpdateCommand.setPassword(passwordEncoder.encode(memberUpdateCommand.getPassword()));

        member.setPassword(memberUpdateCommand.getPassword());
        member.setEmail(memberUpdateCommand.getEmail());
        member.setUsername(memberUpdateCommand.getUsername());
    }
}