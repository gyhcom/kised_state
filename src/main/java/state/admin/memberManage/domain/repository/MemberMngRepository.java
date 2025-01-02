package state.admin.memberManage.domain.repository;

import state.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberMngRepository {

    void save(Member member);

    List<Member> findUserList(String username);

    List<Member> findAll();

    Member findByUserIdOrderBySeqDesc(String userId);

    boolean existsById(int seq);

    Member getReferenceById(int seq);

    Optional<Member> findByUserId(String userId);
}
