package state.member.domain.exception.custom;

import state.admin.memberManage.application.common.error.AdminErrorCode;

public class DepartmentNotExistException extends RuntimeException {
    public DepartmentNotExistException() {
        super(AdminErrorCode.DEPARTMENT_NOT_EXIST.getDescription());
    }
}
