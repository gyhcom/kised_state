package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.entity.Member;
import state.admin.userManage.domain.repository.UserRepository;

import java.util.Optional;

@Component
public class UserFindByIdProcessor {

    private final UserRepository userRepository;

    public UserFindByIdProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Member> execute(int id) {
        return userRepository.findById(id);
    }
}
