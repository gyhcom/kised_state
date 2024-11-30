package state.admin.userManage.application.processor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserRegisterCommand;
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
        userRegisterCommand.setPassword(passwordEncoder.encode(userRegisterCommand.getPassword()));
        userRepository.save(userRegisterCommand.toEntity(userRegisterCommand));
    }
}
