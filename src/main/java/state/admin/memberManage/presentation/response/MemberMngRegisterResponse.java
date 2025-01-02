package state.admin.memberManage.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MemberMngRegisterResponse {

    private int code;
    private String resultMessage;

    public MemberMngRegisterResponse(int code, String resultMessage) {
        this.code = code;
        this.resultMessage = resultMessage;
    }
}
