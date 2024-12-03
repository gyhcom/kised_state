package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class DepartmentNotExistException extends RuntimeException {
    public DepartmentNotExistException() {
        super(ErrorCode.DEPARTMENT_NOT_EXIST.getMessage());
    }
}
