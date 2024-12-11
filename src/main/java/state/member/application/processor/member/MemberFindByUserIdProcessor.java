package state.member.application.processor.member;

import org.springframework.stereotype.Component;
import state.admin.userManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;
import state.member.domain.entity.Member;
import state.member.domain.repository.MemberRepository;

import java.util.Optional;

@Component
public class MemberFindByUserIdProcessor {
    private final MemberRepository memberRepository;
    public MemberFindByUserIdProcessor(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> execute(String userId) {
        // ID 체크
        if(!memberRepository.existsByUserId(userId)) {
            throw new ApiException(ErrorCode.ID_CHECK, "존재하지 않는 ID입니다.");
        }

        return memberRepository.findByUserId(userId);
    }
}
