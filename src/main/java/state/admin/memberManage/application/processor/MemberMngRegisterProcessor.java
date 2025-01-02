package state.admin.memberManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.memberManage.application.command.MemberMngRegisterCommand;
import state.common.exception.ErrorCode;
import state.admin.memberManage.application.common.exception.ApiException;
import state.admin.memberManage.domain.repository.MemberMngRepository;
@Component
public class MemberMngRegisterProcessor {

    private final MemberMngRepository memberMngRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberMngRegisterProcessor(MemberMngRepository memberMngRepository, PasswordEncoder passwordEncoder) {
        this.memberMngRepository = memberMngRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(MemberMngRegisterCommand memberMngRegisterCommand) {
        var userIdchk = memberMngRepository.findByUserId(memberMngRegisterCommand.getUserId()).isPresent();
        if(userIdchk) {
            throw new ApiException(ErrorCode.ID_CHECK);
        } else {
            memberMngRegisterCommand.setPassword(passwordEncoder.encode(memberMngRegisterCommand.getPassword()));
            memberMngRepository.save(memberMngRegisterCommand.toEntity(memberMngRegisterCommand));
        }
    }
}
