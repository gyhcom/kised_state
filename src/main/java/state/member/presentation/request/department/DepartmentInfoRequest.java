package state.member.presentation.request.department;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.member.application.command.department.DepartmentInfoRequestCommand;

@Getter
@Setter
public class DepartmentInfoRequest {
    String departmentCode;
    String departmentName;

    public DepartmentInfoRequestCommand toCommand(DepartmentInfoRequest departmentInfoRequest) {
        return DepartmentInfoRequestCommand.builder()
                .departmentCode(departmentInfoRequest.getDepartmentCode())
                .departmentName(departmentInfoRequest.getDepartmentName())
                .build();
    }
}
