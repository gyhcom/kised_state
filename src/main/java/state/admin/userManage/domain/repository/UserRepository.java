package state.admin.userManage.domain.repository;

import state.admin.userManage.domain.entity.User;
import state.admin.userManage.presentation.request.UserListRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findUserList(String userNm);

    Optional<User> findById(int id);
}
