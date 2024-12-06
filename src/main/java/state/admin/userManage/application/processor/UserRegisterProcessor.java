package state.admin.userManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.common.exception.ErrorCode;
import state.admin.userManage.application.common.exception.ApiException;
import state.admin.userManage.domain.repository.UserRepository;
@Component
public class UserRegisterProcessor {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterProcessor(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(UserRegisterCommand userRegisterCommand) {
        var userIdchk = userRepository.findByUserId(userRegisterCommand.getUserId()).isPresent();
        if(userIdchk) {
            throw new ApiException(ErrorCode.ID_CHECK);
            //Api.ERROR(ErrorCode.ID_CHECK);
            // throw new IllegalArgumentException("중복된 ID입니다.");
        } else {
            userRegisterCommand.setPassword(passwordEncoder.encode(userRegisterCommand.getPassword()));
            userRepository.save(userRegisterCommand.toEntity(userRegisterCommand));
        }
    }
}
