package state.member.domain.exception.custom;

import state.admin.memberManage.application.common.error.AdminErrorCode;

public class PasswordNotMatchedExcpetion extends RuntimeException {
    public PasswordNotMatchedExcpetion() {
        super(MemberErrorCode.PASSWORD_NOT_MATCHED.getDescription());
    }
}