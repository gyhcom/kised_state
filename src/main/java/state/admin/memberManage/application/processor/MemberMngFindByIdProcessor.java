package state.admin.memberManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.memberManage.domain.repository.MemberMngRepository;
import state.member.domain.entity.Member;

@Component
public class MemberMngFindByIdProcessor {

    private final MemberMngRepository memberMngRepository;

    public MemberMngFindByIdProcessor(MemberMngRepository memberMngRepository) {
        this.memberMngRepository = memberMngRepository;
    }

    public Member execute(String userId) {
        return memberMngRepository.findByUserIdOrderBySeqDesc(userId);
    }
}
