package state.member.application.processor.member;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Member;
import state.member.domain.exception.MemberNotFoundException;
import state.member.domain.repository.MemberRepository;

@Component
public class MemberDeleteProcessor {
    private final MemberRepository memberRepository;

    public MemberDeleteProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void execute(int seq, String userId, String username) {
        // 사용자 존재여부 확인
        if (!memberRepository.existsBySeqAndUserIdAndUsername(seq, userId, username)) {
            throw new MemberNotFoundException();
        }

        Member member = memberRepository.getReferenceById(seq);

        // 사용자 논리적으로 삭제
        member.setDeleteYn("Y");
    }
}
