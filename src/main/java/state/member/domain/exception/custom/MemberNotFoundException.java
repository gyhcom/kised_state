package state.member.domain.exception.custom;

import state.common.exception.ErrorCode;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getDescription());
    }
}