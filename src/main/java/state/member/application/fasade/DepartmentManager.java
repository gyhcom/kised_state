package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.command.department.DepartmentInfoRequestCommand;
import state.member.application.processor.department.DepartmentFindAllProcessor;
import state.member.domain.entity.Department;
import state.member.domain.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DepartmentManager {
    private final DepartmentFindAllProcessor departmentFindAllProcessor;

    public DepartmentManager(DepartmentFindAllProcessor departmentFindAllProcessor) {
        this.departmentFindAllProcessor = departmentFindAllProcessor;
    }
    public List<Department> findAll() {
        return departmentFindAllProcessor.execute();
    }
}
