package state.admin.memberManage.infrastructure;

import org.springframework.stereotype.Repository;
import state.admin.memberManage.domain.repository.MemberMngRepository;
import state.admin.memberManage.infrastructure.jpa.AdminJpaRepository;
import state.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepositoryAdaptor implements MemberMngRepository {

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
    public Member findByUserIdOrderBySeqDesc(String userId) {
        return adminJpaRepository.findByUserIdOrderBySeqDesc(userId);
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
