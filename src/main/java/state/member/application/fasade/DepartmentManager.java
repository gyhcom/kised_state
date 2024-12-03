package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.command.department.DepartmentInfoRequestCommand;
import state.member.domain.entity.Department;
import state.member.domain.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DepartmentManager {
    private final DepartmentRepository departmentRepository;

    public DepartmentManager(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
