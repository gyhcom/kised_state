package state.member.application.fasade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import state.member.application.processor.department.DepartmentFindAllProcessor;
import state.member.application.processor.department.DepartmentFindByIdProcessor;
import state.member.domain.entity.Department;

import java.util.List;

@Transactional
@Service
public class DepartmentManager {
    private final DepartmentFindAllProcessor departmentFindAllProcessor;
    private final DepartmentFindByIdProcessor departmentFindByIdProcessor;

    public DepartmentManager(
            DepartmentFindAllProcessor departmentFindAllProcessor,
            DepartmentFindByIdProcessor departmentFindByIdProcessor) {
        this.departmentFindAllProcessor = departmentFindAllProcessor;
        this.departmentFindByIdProcessor = departmentFindByIdProcessor;
    }
    public List<Department> findAll() {
        return departmentFindAllProcessor.execute();
    }

    public Department getReferenceById(String id) {return departmentFindByIdProcessor.execute(id);}
}
