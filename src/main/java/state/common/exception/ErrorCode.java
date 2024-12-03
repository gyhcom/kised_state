package state.common.exception;

public enum ErrorCode {
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    DEPARTMENT_NOT_EXIST("부서 정보가 존재하지 않습니다."),
    DUPLICATE_USERID("이미 존재하는 아이디입니다."),
    DUPLICATE_USER("이미 존재하는 이메일입니다."),
    DEPARTMENT_NOT_FOUND("부서 정보를 찾을 수 없습니다."),
    POSITION_NOT_FOUND("직위 정보를 찾을 수 없습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}