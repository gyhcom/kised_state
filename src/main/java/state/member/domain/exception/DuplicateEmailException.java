package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_USER.getMessage());
    }
}
