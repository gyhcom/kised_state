package state.member.application.processor.department;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Department;
import state.member.domain.exception.DepartmentNotExistException;
import state.member.domain.repository.DepartmentRepository;

import java.util.List;

@Component
public class DepartmentFindAllProcessor {
    private final DepartmentRepository departmentRepository;

    public DepartmentFindAllProcessor(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> execute() {
        List<Department> departments = departmentRepository.findAll();
        if(departments.isEmpty()) {
            throw new DepartmentNotExistException();
        }
        return departments;
    }
}
