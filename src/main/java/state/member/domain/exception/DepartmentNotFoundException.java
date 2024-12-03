package state.member.domain.exception;

import state.common.exception.ErrorCode;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException() {
        super(ErrorCode.DEPARTMENT_NOT_FOUND.getMessage());
    }
}
