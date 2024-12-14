package state.admin.userManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.userManage.application.command.UserInfoDeleteCommand;
import state.common.exception.ErrorCode;
import state.admin.userManage.application.common.exception.ApiException;
import state.admin.userManage.domain.auth.AuthRole;
import state.admin.userManage.domain.repository.UserRepository;
import state.member.domain.entity.Member;

@Component
public class UserInfoDeleteProcessor {

    private final UserRepository userRepository;

    public UserInfoDeleteProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserInfoDeleteCommand userInfoDeleteCommand) {

        if (!userRepository.existsById(userInfoDeleteCommand.getSeq())) {
            throw new ApiException(ErrorCode.NULL_POINT, "잘못된 사용자입니다.");
        } else {
            Member user = userRepository.getReferenceById(userInfoDeleteCommand.getSeq());

            user.setUserRole("USER");
        }

    }
}
