package state.member.application.processor.member;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Member;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

import java.util.Optional;

@Component
public class MemberFindByUsernameProcessor {
    private final MemberRepository memberRepository;

    public MemberFindByUsernameProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(String username) {
        if(!memberRepository.existsByUsername(username)) {
            throw new MemberNotFoundException();
        }

        return memberRepository.findByUsername(username);
    }
}
