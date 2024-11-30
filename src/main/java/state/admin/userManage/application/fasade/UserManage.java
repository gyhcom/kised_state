package state.admin.userManage.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.admin.userManage.application.command.UserRegisterCommand;
import state.admin.userManage.application.processor.UserRegisterProcessor;

@Transactional
@Service
public class UserManage {

    private final UserRegisterProcessor userRegisterProcessor;

    public UserManage(UserRegisterProcessor userRegisterProcessor) {
        this.userRegisterProcessor = userRegisterProcessor;
    }

    public void save(UserRegisterCommand userRegisterCommand) {
        userRegisterProcessor.execute(userRegisterCommand);
    }

}
