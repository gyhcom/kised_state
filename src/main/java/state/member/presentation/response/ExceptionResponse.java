package state.member.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private Integer code;
    private String message;
}
