package state.admin.userManage.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminJpaRepository extends JpaRepository<Member, Integer> {
    List<Member> findByusernameOrderBySeqDesc(String name);

    Optional<Member> findByUserId(String userId);

    Member findBySeq(int seq);

    Member findByUserIdOrderBySeqDesc(String userId);
}
