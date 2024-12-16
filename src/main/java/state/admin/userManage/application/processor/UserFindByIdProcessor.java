package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.repository.UserRepository;
import state.member.domain.entity.Member;

@Component
public class UserFindByIdProcessor {

    private final UserRepository userRepository;

    public UserFindByIdProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Member execute(int id) {
        return userRepository.findById(id);
    }
}
