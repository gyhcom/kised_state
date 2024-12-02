package state.admin.userManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.entity.Member;
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
    public void save(Member user) {
        adminJpaRepository.save(user);
    }

    @Override
    public List<Member> findUserList(String userNm) {
        return adminJpaRepository.findByUserNmOrderBySeqDesc(userNm);
    }

    @Override
    public Optional<Member> findById(int id) {
        return adminJpaRepository.findById(id);
    }
}
