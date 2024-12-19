package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.command.member.MemberModPasswordCommand;
import state.member.application.command.member.MemberRegisterCommand;
import state.member.application.command.member.MemberUpdateCommand;
import state.member.application.processor.member.*;
import state.member.domain.entity.Member;

import java.util.Optional;

@Transactional
@Service
public class MemberManager {
    private final MemberRegisterProcessor memberRegisterProcessor;
    private final MemberFindByIdProcessor memberFindByIdProcessor;
    private final MemberDeleteProcessor memberDeleteProcessor;
    private final MemberUpdateProcessor memberUpdateProcessor;
    private final MemberFindByUserIdProcessor memberFindByUserIdProcessor;
    private final MemberModifyPasswordProcessor memberModifyPasswordProcessor;

    public MemberManager(
            MemberRegisterProcessor memberRegisterProcessor,
            MemberFindByIdProcessor memberFindByIdProcessor,
            MemberDeleteProcessor memberDeleteProcessor,
            MemberUpdateProcessor memberUpdateProcessor,
            MemberFindByUserIdProcessor memberFindByUserIdProcessor,
            MemberModifyPasswordProcessor memberModifyPasswordProcessor) {
        this.memberRegisterProcessor = memberRegisterProcessor;
        this.memberFindByIdProcessor = memberFindByIdProcessor;
        this.memberDeleteProcessor = memberDeleteProcessor;
        this.memberUpdateProcessor = memberUpdateProcessor;
        this.memberFindByUserIdProcessor = memberFindByUserIdProcessor;
        this.memberModifyPasswordProcessor = memberModifyPasswordProcessor;
    }

    public void save(MemberRegisterCommand memberRegisterCommand) {
        memberRegisterProcessor.execute(memberRegisterCommand);
    }

    public Optional<Member> findById(int seq) {
        return memberFindByIdProcessor.execute(seq);
    }

    public void delete(int seq, String userId, String username) {
        memberDeleteProcessor.execute(seq, userId, username);
    }

    //getReferenceById 와 findById의 차이점 알기
    public void update(MemberUpdateCommand memberUpdateCommand) {
        memberUpdateProcessor.execute(memberUpdateCommand);
    }

    public Optional<Member> findByUserId(String userId) {
        return memberFindByUserIdProcessor.execute(userId);
    }

    public void modifyPassword(String userId, String email, MemberModPasswordCommand memberModPasswordCommand) {
        memberModifyPasswordProcessor.execute(userId, email, memberModPasswordCommand);
    }
}
