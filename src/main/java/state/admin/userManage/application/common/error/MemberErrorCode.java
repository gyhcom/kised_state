package state.admin.userManage.application.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode {

    // TODO 사용자 에러코드 몇번으로 할지 정해야됨.
    USER_NOT_FOUND(400,1401,"사용자 없음"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
