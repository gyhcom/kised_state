package state.admin.userManage.domain.repository;

import state.admin.userManage.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(Member member);

    List<Member> findUserList(String userNm);

    Optional<Member> findById(int id);
}
