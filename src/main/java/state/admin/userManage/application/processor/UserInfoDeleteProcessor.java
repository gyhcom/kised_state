package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserInfoDeleteCommand;
import state.admin.userManage.domain.auth.AuthRole;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;

@Component
public class UserInfoDeleteProcessor {

    private final UserRepository userRepository;

    public UserInfoDeleteProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserInfoDeleteCommand userInfoDeleteCommand) {

        if (!userRepository.existsById(userInfoDeleteCommand.getSeq())) {
            throw new NullPointerException();
        } else {
            User user = userRepository.getReferenceById(userInfoDeleteCommand.getSeq());

            user.setUserRole(AuthRole.USER);
        }

    }
}
