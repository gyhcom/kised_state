package state.admin.userManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.infrastructure.jpa.AdminJpaRepository;
@Repository
public class AdminRepositoryAdaptor implements UserRepository {

    private final AdminJpaRepository adminJpaRepository;

    public AdminRepositoryAdaptor(AdminJpaRepository adminJpaRepository) {
        this.adminJpaRepository = adminJpaRepository;
    }

    @Override
    public void save(User user) {
        adminJpaRepository.save(user);
    }
}
