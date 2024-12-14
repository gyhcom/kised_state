package state.admin.userManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.userManage.domain.repository.UserRepository;
import state.admin.userManage.infrastructure.jpa.AdminJpaRepository;
import state.member.domain.entity.Member;

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
    public List<Member> findUserList(String username) {
        return adminJpaRepository.findByusernameOrderBySeqDesc(username);
    }

    @Override
    public List<Member> findAll() {
        return adminJpaRepository.findAll();
    }

    @Override
    public Member findById(int seq) {
        return adminJpaRepository.findBySeq(seq);
    }

    @Override
    public boolean existsById(int seq) {
        return adminJpaRepository.existsById(seq);
    }

    @Override
    public Member getReferenceById(int seq) {
        return adminJpaRepository.getReferenceById(seq);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return adminJpaRepository.findByUserId(userId);
    }


}
