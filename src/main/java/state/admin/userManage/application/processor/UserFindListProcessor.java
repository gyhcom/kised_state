package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.domain.repository.UserRepository;
import state.member.domain.entity.Member;

import java.util.List;

@Component
public class UserFindListProcessor {

    private final UserRepository userRepository;

    public UserFindListProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Member> execute(String username) {
        List<Member> userList;
        if(username != null) {
            userList = userRepository.findUserList(username);
        } else {
            userList = userRepository.findAll();
        }
        return userList;
    }
}
