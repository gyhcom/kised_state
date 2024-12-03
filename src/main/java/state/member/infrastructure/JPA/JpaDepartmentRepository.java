package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.Department;

@Repository
public interface JpaDepartmentRepository extends JpaRepository<Department, String> {
}
