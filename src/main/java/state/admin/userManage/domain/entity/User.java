package state.admin.userManage.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.admin.userManage.presentation.response.UserResponse;
import state.admin.userManage.domain.auth.AuthRole;

@Getter
@Setter
@Entity
@Table(name = "user")
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private int seq;

    @Column(name = "USER_ID", length = 30)
    private String userId;

    @Column(name = "USER_NM", length = 15)
    private String userNm;

    @Column(name = "PASSWORD", length = 12)
    private String password;

    @Column(name = "USER_ROLE", length = 10)
    @Enumerated(EnumType.STRING)
    private AuthRole userRole;

    @Column(name = "EMAIL", length = 30)
    private String email;

    @Column(name = "DEPT_CD", length = 3)
    private String deptCd;

    @Column(name = "MANAGE_CD", length = 3)
    private String manageCd;

    public User() {
    }

    public User(int seq, String userId, String userNm, String password, AuthRole userRole, String email, String deptCd, String manageCd) {
        this.seq = seq;
        this.userId = userId;
        this.userNm = userNm;
        this.password = password;
        this.userRole = userRole;
        this.email = email;
        this.deptCd = deptCd;
        this.manageCd = manageCd;
    }

    public UserResponse toCommand() {
        return UserResponse.builder()
                .userId(this.userId)
                .userNm(this.userNm)
                .userRole(this.userRole)
                .email(this.email)
                .deptCd(this.deptCd)
                .manageCd(this.manageCd)
                .build();
    }
}
