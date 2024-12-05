package state.member.application.processor.member;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Member;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

import java.util.Optional;

@Component
public class MemberFindByIdProcessor {
    private final MemberRepository memberRepository;

    public MemberFindByIdProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(int seq) {
        if (!memberRepository.existsById(seq)) {
            throw new MemberNotFoundException();
        }
        return memberRepository.findById(seq);
    }
}
