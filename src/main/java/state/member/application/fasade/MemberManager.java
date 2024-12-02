package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final MemberFindByUsernameProcessor memberFindByUsernameProcessor;
    private final MemberDeleteProcessor memberDeleteProcessor;
    private final MemberUpdateProcessor memberUpdateProcessor;

    public MemberManager(
            MemberRegisterProcessor memberRegisterProcessor,
            MemberFindByIdProcessor memberFindByIdProcessor,
            MemberFindByUsernameProcessor memberFindByUsernameProcessor,
            MemberDeleteProcessor memberDeleteProcessor,
            MemberUpdateProcessor memberUpdateProcessor) {
        this.memberRegisterProcessor = memberRegisterProcessor;
        this.memberFindByIdProcessor = memberFindByIdProcessor;
        this.memberFindByUsernameProcessor = memberFindByUsernameProcessor;
        this.memberDeleteProcessor = memberDeleteProcessor;
        this.memberUpdateProcessor = memberUpdateProcessor;
    }

    public void save(MemberRegisterCommand memberRegisterCommand) {
        memberRegisterProcessor.execute(memberRegisterCommand);
    }

    public Optional<Member> findById(int seq) {
        return memberFindByIdProcessor.execute(seq);
    }

    public Optional<Member> findByUsername(String userId) {
        return memberFindByUsernameProcessor.execute(userId);
    }

    public void delete(int seq) {
        memberDeleteProcessor.execute(seq);
    }

    //getReferenceById 와 findById의 차이점 알기
    public void update(MemberUpdateCommand memberUpdateCommand) {
        memberUpdateProcessor.execute(memberUpdateCommand);
    }
}
