package state.admin.userManage.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.application.processor.UserFindByIdProcessor;
import state.admin.userManage.application.processor.UserFindListProcessor;
import state.admin.userManage.application.processor.UserRegisterProcessor;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserManage {

    private final UserRegisterProcessor userRegisterProcessor;
    private final UserFindByIdProcessor userFindByIdProcessor;
    private final UserFindListProcessor userFindListProcessor;

    public UserManage(UserRegisterProcessor userRegisterProcessor, UserFindByIdProcessor userFindByIdProcessor, UserFindListProcessor userFindListProcessor) {
        this.userRegisterProcessor = userRegisterProcessor;
        this.userFindByIdProcessor = userFindByIdProcessor;
        this.userFindListProcessor = userFindListProcessor;
    }

    public void save(UserRegisterCommand userRegisterCommand) {
        userRegisterProcessor.execute(userRegisterCommand);
    }

    public List<User> findList(UserListRequest userListRequest) {
        return userFindListProcessor.execute(userListRequest);
    }
    public Optional<User> findById(int seq) {
        return userFindByIdProcessor.execute(seq);
    }

}
