package state.admin.userManage.domain.auth;

import lombok.Getter;

@Getter
public enum AuthRole {

    // TODO 권한 부여 ( 가입시 : ADMIN, 탈퇴시 : UNMANAGER )
    ADMIN,
    UNMANAGE
}
