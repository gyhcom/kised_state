package state.member.application.processor;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Member;
import state.member.domain.repository.MemberRepository;

import java.util.Optional;

@Component
public class MemberFindByIdProcessor {
    private final MemberRepository memberRepository;

    public MemberFindByIdProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(int id) {
        return memberRepository.findById(id);
    }
}
