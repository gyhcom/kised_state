package state.member.domain.exception.custom;

import state.admin.memberManage.application.common.error.AdminErrorCode;
import state.common.exception.ErrorCode;

public class PositionNotExistException extends RuntimeException {
    public PositionNotExistException() {
        super(AdminErrorCode.POSITION_NOT_FOUND.getDescription());
    }
}
