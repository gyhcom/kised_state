package state.member.domain.repository;

import state.member.domain.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> findAll();
    Boolean existsById(String id);
}
