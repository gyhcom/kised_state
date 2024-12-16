package state.member.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import state.member.domain.exception.custom.DuplicateEmailException;
import state.member.domain.exception.custom.MemberErrorCode;
import state.member.presentation.response.ExceptionResponse;

@Slf4j
@RestControllerAdvice
@Order(1)
public class MemberExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> emailExceptionHandler(DuplicateEmailException duplicateEmailException) {
        log.error("", duplicateEmailException);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .code(MemberErrorCode.DUPLICATE_EMAIL.getErrorCode())
                                .message(MemberErrorCode.DUPLICATE_EMAIL.getDescription())
                                .build()
                );
    }
}
