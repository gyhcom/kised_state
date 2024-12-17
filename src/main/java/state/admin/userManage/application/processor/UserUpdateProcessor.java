package state.admin.userManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserInfoUpdateCommand;
import state.common.exception.ErrorCode;
import state.admin.userManage.application.common.exception.ApiException;
import state.admin.userManage.domain.repository.UserRepository;
import state.member.domain.entity.Member;

@Component
public class UserUpdateProcessor {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserUpdateProcessor(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(UserInfoUpdateCommand userInfoUpdateCommand) {

        if (!userRepository.existsById(userInfoUpdateCommand.getSeq())) {
            throw new ApiException(ErrorCode.NULL_POINT);
        } else {
            Member member = userRepository.getReferenceById(userInfoUpdateCommand.getSeq());
            //비밀번호 암호화
            userInfoUpdateCommand.setPassword(passwordEncoder.encode(userInfoUpdateCommand.getPassword()));

            member.setUserRole(String.valueOf(userInfoUpdateCommand.getUserRole()));
            member.setDepartmentCode(userInfoUpdateCommand.getDepartmentCode());
            member.setPositionCode(userInfoUpdateCommand.getPositionCode());
        }
    }
}
