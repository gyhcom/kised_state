package state.member.application.processor.member;

import org.springframework.stereotype.Component;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberDeleteProcessor {
    private final MemberRepository memberRepository;

    public MemberDeleteProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void execute(int seq) {
        if (!memberRepository.existsById(seq)) {
            throw new MemberNotFoundException();
        }
        memberRepository.deleteById(seq);
    }
}
