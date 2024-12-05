package state.admin.userManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserInfoUpdateCommand;
import state.admin.userManage.application.common.error.ErrorCode;
import state.admin.userManage.application.common.exception.ApiException;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;

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
            User user = userRepository.getReferenceById(userInfoUpdateCommand.getSeq());
            //비밀번호 암호화
            userInfoUpdateCommand.setPassword(passwordEncoder.encode(userInfoUpdateCommand.getPassword()));

            user.setUserNm(userInfoUpdateCommand.getUserNm());
            user.setEmail(userInfoUpdateCommand.getEmail());
            user.setDeptCd(userInfoUpdateCommand.getDeptCd());
            user.setManageCd(userInfoUpdateCommand.getManageCd());
        }
    }
}
