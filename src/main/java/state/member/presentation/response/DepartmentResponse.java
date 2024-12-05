package state.member.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DepartmentResponse {
    String departmentCode;
    String departmentName;
}
