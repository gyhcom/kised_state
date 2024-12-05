package state.admin.userManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.infrastructure.jpa.AdminJpaRepository;

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
        return adminJpaRepository.findByUserNmOrderBySeqDesc(userNm);
    }

    @Override
    public User findById(int seq) {
        return adminJpaRepository.findBySeq(seq);
    }

    @Override
    public boolean existsById(int seq) {
        return adminJpaRepository.existsById(seq);
    }

    @Override
    public User getReferenceById(int seq) {
        return adminJpaRepository.getReferenceById(seq);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return adminJpaRepository.findByUserId(userId);
    }


}
