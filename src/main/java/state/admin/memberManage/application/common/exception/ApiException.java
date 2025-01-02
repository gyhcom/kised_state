package state.admin.memberManage.application.common.exception;

import lombok.Getter;
import state.common.exception.ErrorCode;
@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String errorDescription;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, String errorDescription) {
        super(errorDescription); // 내가 지정한 메시지 부모로 전달
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCode errorCode, Throwable tx) {
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, Throwable tx, String errorDescription) {
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
