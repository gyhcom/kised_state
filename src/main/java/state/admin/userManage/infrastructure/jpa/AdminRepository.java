package state.admin.userManage.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import state.admin.userManage.domain.AdminManageEntity;

public interface AdminRepository extends JpaRepository<AdminManageEntity, Integer> {
}
