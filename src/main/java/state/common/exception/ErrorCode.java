package state.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    OK(200,200,"성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청 입니다."),
    ID_CHECK(HttpStatus.BAD_REQUEST.value(), 409, "중복된 아이디 입니다."),

    RESPONSE_FORMAT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), 501, "응답 데이터 형식이 올바르지 않습니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "사용자를 찾을 수 없습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}