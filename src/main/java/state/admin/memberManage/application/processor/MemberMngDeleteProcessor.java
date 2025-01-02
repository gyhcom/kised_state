package state.admin.memberManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.memberManage.application.command.MemberMngDeleteCommand;
import state.common.exception.ErrorCode;
import state.admin.memberManage.application.common.exception.ApiException;
import state.admin.memberManage.domain.repository.MemberMngRepository;
import state.member.domain.entity.Member;

@Component
public class MemberMngDeleteProcessor {

    private final MemberMngRepository memberMngRepository;

    public MemberMngDeleteProcessor(MemberMngRepository memberMngRepository) {
        this.memberMngRepository = memberMngRepository;
    }

    public void execute(MemberMngDeleteCommand memberMngDeleteCommand) {

        if (!memberMngRepository.existsById(memberMngDeleteCommand.getSeq())) {
            throw new ApiException(ErrorCode.NULL_POINT, "잘못된 사용자입니다.");
        } else {
            Member member = memberMngRepository.getReferenceById(memberMngDeleteCommand.getSeq());

            member.setUserRole("USER");
            member.setDeleteYn("Y");

        }

    }
}
