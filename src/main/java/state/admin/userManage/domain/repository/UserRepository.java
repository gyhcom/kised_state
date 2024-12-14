package state.admin.userManage.domain.repository;

import state.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(Member user);

    List<Member> findUserList(String username);

    Member findById(int seq);

    boolean existsById(int seq);

    Member getReferenceById(int seq);

    Optional<Member> findByUserId(String userId);
}
