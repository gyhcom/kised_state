package state.admin.userManage.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.admin.userManage.domain.auth.AuthRole;

@Setter
@Getter
@Builder
public class UserRegisterResponse {

    private int code;
    private String resultMessage;

    public UserRegisterResponse(int code, String resultMessage) {
        this.code = code;
        this.resultMessage = resultMessage;
    }
}
