package state.admin.userManage.domain.repository;

import state.admin.userManage.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findUserList(String userNm);

    Optional<User> findById(int seq);

    boolean existsById(int seq);

    User getReferenceById(int seq);
}
