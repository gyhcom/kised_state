package state.member.application.processor.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.member.application.command.member.MemberUpdateCommand;
import state.member.domain.entity.Member;
import state.member.domain.exception.DepartmentNotFoundException;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.exception.PositionNotExistException;
import state.member.domain.repository.DepartmentRepository;
import state.member.domain.repository.MemberRepository;
import state.member.domain.repository.PositionRepository;

@Component
public class MemberUpdateProcessor {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberUpdateProcessor(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            DepartmentRepository departmentRepository,
            PositionRepository positionRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    public void execute(MemberUpdateCommand memberUpdateCommand) {
        // 사용자 존재 여부 확인
        if(!memberRepository.existsById(memberUpdateCommand.getSeq())) {
            throw new MemberNotFoundException();
        }

        Member member = memberRepository.getReferenceById(memberUpdateCommand.getSeq());

        //비밀번호 암호화
        memberUpdateCommand.setPassword(passwordEncoder.encode(memberUpdateCommand.getPassword()));

        member.setPassword(memberUpdateCommand.getPassword());
        member.setEmail(memberUpdateCommand.getEmail());
        member.setUsername(memberUpdateCommand.getUsername());
    }
}