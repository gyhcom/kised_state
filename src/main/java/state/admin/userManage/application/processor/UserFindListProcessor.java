package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;

@Component
public class UserFindListProcessor {

    private final UserRepository userRepository;

    public UserFindListProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute(UserListRequest userListRequest) {
        return userRepository.findUserList(userListRequest.getUserNm());
    }
}
