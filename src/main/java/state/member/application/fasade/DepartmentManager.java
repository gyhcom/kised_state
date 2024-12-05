package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.processor.department.DepartmentFindAllProcessor;
import state.member.domain.entity.Department;

import java.util.List;

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
