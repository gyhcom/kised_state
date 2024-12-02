package state.admin.userManage.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.entity.User;

import java.util.List;

@Repository
public interface AdminJpaRepository extends JpaRepository<User, Integer> {
    List<User> findByUserNmOrderBySeqDesc(String name);
}
