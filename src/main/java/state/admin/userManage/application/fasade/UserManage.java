package state.admin.userManage.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.admin.userManage.application.command.UserInfoDeleteCommand;
import state.admin.userManage.application.command.UserInfoUpdateCommand;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.application.processor.*;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserManage {

    private final UserRegisterProcessor userRegisterProcessor;
    private final UserFindByIdProcessor userFindByIdProcessor;
    private final UserFindListProcessor userFindListProcessor;
    private final UserUpdateProcessor userUpdateProcessor;
    private final UserInfoDeleteProcessor userInfoDeleteProcessor;

    public UserManage(UserRegisterProcessor userRegisterProcessor, UserFindByIdProcessor userFindByIdProcessor, UserFindListProcessor userFindListProcessor
            , UserUpdateProcessor userUpdateProcessor, UserInfoDeleteProcessor userInfoDeleteProcessor) {
        this.userRegisterProcessor = userRegisterProcessor;
        this.userFindByIdProcessor = userFindByIdProcessor;
        this.userFindListProcessor = userFindListProcessor;
        this.userUpdateProcessor = userUpdateProcessor;
        this.userInfoDeleteProcessor = userInfoDeleteProcessor;
    }

    public void save(UserRegisterCommand userRegisterCommand) {
        userRegisterProcessor.execute(userRegisterCommand);
    }

    public void update(UserInfoUpdateCommand userInfoUpdateCommand) {
        userUpdateProcessor.execute(userInfoUpdateCommand);
    }

    public void delete(UserInfoDeleteCommand userInfoDeleteCommand) {
        userInfoDeleteProcessor.execute(userInfoDeleteCommand);
    }

    public List<User> findList(String userNm) {
        return userFindListProcessor.execute(userNm);
    }
    public User findById(int seq) {
        return userFindByIdProcessor.execute(seq);
    }

}
