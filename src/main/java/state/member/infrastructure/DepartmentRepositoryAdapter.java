package state.member.infrastructure;

import org.springframework.stereotype.Repository;
import state.member.domain.entity.Department;
import state.member.domain.repository.DepartmentRepository;
import state.member.infrastructure.JPA.JpaDepartmentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryAdapter implements DepartmentRepository {
    private final JpaDepartmentRepository jpaDepartmentRepository;
    public DepartmentRepositoryAdapter(JpaDepartmentRepository jpaDepartmentRepository) {
        this.jpaDepartmentRepository = jpaDepartmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return jpaDepartmentRepository.findAll();
    }
}
