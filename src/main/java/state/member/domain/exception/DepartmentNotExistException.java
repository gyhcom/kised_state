package state.member.domain.exception;

import state.admin.userManage.application.common.error.AdminErrorCode;

public class DepartmentNotExistException extends RuntimeException {
    public DepartmentNotExistException() {
        super(AdminErrorCode.DEPARTMENT_NOT_EXIST.getDescription());
    }
}
