package state.admin.memberManage.application.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AdminErrorCode {
    DEPARTMENT_NOT_EXIST(HttpStatus.NOT_FOUND.value(), 439, "부서 정보가 존재하지 않습니다."),
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 438,"직위 정보를 찾을 수 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
