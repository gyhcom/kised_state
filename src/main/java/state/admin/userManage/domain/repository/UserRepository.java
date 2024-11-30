package state.admin.userManage.domain.repository;

import state.admin.userManage.domain.entity.User;

public interface UserRepository {

    void save(User user);
}
