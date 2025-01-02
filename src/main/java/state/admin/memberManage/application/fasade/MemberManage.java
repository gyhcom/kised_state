package state.admin.memberManage.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.admin.memberManage.application.command.MemberMngDeleteCommand;
import state.admin.memberManage.application.command.MemberMngUpdateCommand;
import state.admin.memberManage.application.command.MemberMngRegisterCommand;
import state.admin.memberManage.application.processor.*;
import state.member.domain.entity.Member;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class MemberManage {

    private final MemberMngRegisterProcessor memberMngRegisterProcessor;
    private final MemberMngFindByIdProcessor memberMngFindByIdProcessor;
    private final MemberMngFindListProcessor memberMngFindListProcessor;
    private final MemberMngUpdateProcessor memberMngUpdateProcessor;
    private final MemberMngDeleteProcessor memberMngDeleteProcessor;

    public MemberManage(MemberMngRegisterProcessor memberMngRegisterProcessor, MemberMngFindByIdProcessor memberMngFindByIdProcessor, MemberMngFindListProcessor memberMngFindListProcessor
            , MemberMngUpdateProcessor memberMngUpdateProcessor, MemberMngDeleteProcessor memberMngDeleteProcessor) {
        this.memberMngRegisterProcessor = memberMngRegisterProcessor;
        this.memberMngFindByIdProcessor = memberMngFindByIdProcessor;
        this.memberMngFindListProcessor = memberMngFindListProcessor;
        this.memberMngUpdateProcessor = memberMngUpdateProcessor;
        this.memberMngDeleteProcessor = memberMngDeleteProcessor;
    }

    public void save(MemberMngRegisterCommand memberMngRegisterCommand) {
        memberMngRegisterProcessor.execute(memberMngRegisterCommand);
    }

    public void update(MemberMngUpdateCommand memberMngUpdateCommand) {
        memberMngUpdateProcessor.execute(memberMngUpdateCommand);
    }

    public void delete(MemberMngDeleteCommand memberMngDeleteCommand) {
        memberMngDeleteProcessor.execute(memberMngDeleteCommand);
    }

    public List<Member> findList(String username) {
        return memberMngFindListProcessor.execute(username);
    }
    public Member findById(String userId) {
        return memberMngFindByIdProcessor.execute(userId);
    }

}
