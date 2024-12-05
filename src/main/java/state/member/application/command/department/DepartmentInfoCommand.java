package state.member.application.command.department;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import state.member.domain.entity.Department;

@Getter
@Setter
@Builder
public class DepartmentInfoCommand {
    String departmentCode;
    String departmentName;

    public Department toEntity(DepartmentInfoCommand departmentInfoCommand) {
        return Department.builder()
                .departmentCode(departmentInfoCommand.getDepartmentCode())
                .departmentName(departmentInfoCommand.getDepartmentName())
                .build();
    }
}
