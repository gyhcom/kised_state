package state.member.application.processor.department;

import org.springframework.stereotype.Component;
import state.member.domain.entity.Department;
import state.member.domain.exception.custom.DepartmentNotExistException;
import state.member.domain.repository.DepartmentRepository;

@Component
public class DepartmentFindByIdProcessor {
    private final DepartmentRepository departmentRepository;
    public DepartmentFindByIdProcessor(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department execute(String id) {
        if( !departmentRepository.existsById(id) ) throw new DepartmentNotExistException();

        return departmentRepository.getReferenceById(id);
    }
}
