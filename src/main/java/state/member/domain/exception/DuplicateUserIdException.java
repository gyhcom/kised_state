package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super(ErrorCode.DUPLICATE_USERID.getMessage());
    }
}
