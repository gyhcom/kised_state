package state.admin.userManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.infrastructure.jpa.AdminJpaRepository;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<User> findUserList(String userNm) {
        return adminJpaRepository.findByUserNm(userNm);
    }

    @Override
    public Optional<User> findById(int id) {
        return adminJpaRepository.findById(id);
    }
}
