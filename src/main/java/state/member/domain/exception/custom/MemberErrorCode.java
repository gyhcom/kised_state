package state.member.domain.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    DUPLICATE_USERID(HttpStatus.BAD_REQUEST.value(), 400, "이미 존재하는 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST.value(), 400, "이미 존재하는 이메일입니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
