package state.admin.userManage.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.application.command.UserInfoDeleteCommand;

@Getter
@Setter
public class UserInfoDeleteRequest {

    int seq;

    public UserInfoDeleteCommand toCommand(UserInfoDeleteRequest userInfoDeleteRequest) {
        return UserInfoDeleteCommand.builder()
                .seq(userInfoDeleteRequest.getSeq())
                .build();
    }
}
