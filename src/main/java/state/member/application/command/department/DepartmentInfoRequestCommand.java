package state.member.application.command.department;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.member.domain.entity.Department;

@Getter
@Setter
@Builder
public class DepartmentInfoRequestCommand {
    String departmentCode;
    String departmentName;

    public Department toEntity(DepartmentInfoRequestCommand departmentInfoRequestCommand) {
        return Department.builder()
                .departmentCode(departmentInfoRequestCommand.getDepartmentCode())
                .departmentName(departmentInfoRequestCommand.getDepartmentName())
                .build();
    }
}
