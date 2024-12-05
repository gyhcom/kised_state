package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class PositionNotExistException extends RuntimeException {
    public PositionNotExistException() {
        super(ErrorCode.POSITION_NOT_FOUND.getMessage());
    }
}
