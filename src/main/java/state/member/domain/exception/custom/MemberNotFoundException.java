package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getDescription());
    }
}