package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.entity.Member;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;

@Component
public class UserListProcessor {

    private final UserRepository userRepository;

    public UserListProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Member> execute(UserListRequest userListRequest) {
        return userRepository.findUserList(userListRequest.getUserNm());
    }
}