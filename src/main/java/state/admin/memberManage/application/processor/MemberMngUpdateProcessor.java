package state.admin.memberManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.memberManage.application.command.MemberMngUpdateCommand;
import state.common.exception.ErrorCode;
import state.admin.memberManage.application.common.exception.ApiException;
import state.admin.memberManage.domain.repository.MemberMngRepository;
import state.member.domain.entity.Member;

@Component
public class MemberMngUpdateProcessor {

    private final MemberMngRepository memberMngRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberMngUpdateProcessor(MemberMngRepository memberMngRepository, PasswordEncoder passwordEncoder) {
        this.memberMngRepository = memberMngRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(MemberMngUpdateCommand memberMngUpdateCommand) {

        if (!memberMngRepository.existsById(memberMngUpdateCommand.getSeq())) {
            throw new ApiException(ErrorCode.NULL_POINT);
        } else {
            Member member = memberMngRepository.getReferenceById(memberMngUpdateCommand.getSeq());
            //비밀번호 암호화
            memberMngUpdateCommand.setPassword(passwordEncoder.encode(memberMngUpdateCommand.getPassword()));

            member.setUserRole(String.valueOf(memberMngUpdateCommand.getUserRole()));
            member.setDepartmentCode(memberMngUpdateCommand.getDepartmentCode());
            member.setPositionCode(memberMngUpdateCommand.getPositionCode());
        }
    }
}
