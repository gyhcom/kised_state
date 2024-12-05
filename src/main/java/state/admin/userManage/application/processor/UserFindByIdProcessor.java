package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;

@Component
public class UserFindByIdProcessor {

    private final UserRepository userRepository;

    public UserFindByIdProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(int id) {
        return userRepository.findById(id);
    }
}
